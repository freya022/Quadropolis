package com.freya02.quadropolis.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.function.Function;

public class Utils {
	private static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

	@Contract("_, _ -> new")
	@NotNull
	public static <T> T loadRoot(String resourceName, Object controller) throws IOException {
		final URL resource = WALKER.getCallerClass().getResource(resourceName);

		final FXMLLoader loader = new FXMLLoader(resource);
		loader.setController(controller);

		return loader.load();
	}

	@Contract("_, _ -> new")
	@NotNull
	public static Scene loadScene(String resourceName, Object controller) throws IOException {
		final URL resource = WALKER.getCallerClass().getResource(resourceName);

		final FXMLLoader loader = new FXMLLoader(resource);
		loader.setController(controller);
		final Parent root = loader.load();

		return new Scene(root);
	}

	@NotNull
	public static Stage newStage() {
		final Stage stage = new Stage();
		stage.setResizable(false);
		stage.setTitle("Quadropolis");

		return stage;
	}

	@NotNull
	public static <T> LoadedStage<T> loadAndShow(String resourceName, Function<Stage, T> controllerFunction) throws IOException {
		final Stage stage = newStage();

		final T controller = controllerFunction.apply(stage);
		final URL resource = WALKER.getCallerClass().getResource(resourceName);

		final FXMLLoader loader = new FXMLLoader(resource);
		loader.setController(controller);
		final Parent root = loader.load();

		final Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		return new LoadedStage<>(stage, scene, controller);
	}
}
