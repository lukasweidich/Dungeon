package contrib.hud;

import contrib.hud.dialogs.OkDialog;
import core.Entity;
import core.utils.IVoidFunction;
import task.Task;
import task.game.hud.NewDSLQuizUI;
import task.game.hud.UIAnswerCallback;
import task.tasktype.Quiz;

import java.util.Iterator;
import java.util.function.Function;

/**
 * The DialogUtils class is responsible for displaying text popups and quizzes to the player.
 *
 * @see OkDialog
 * @see NewDSLQuizUI
 * @see Quiz
 */
public class NewDSLDialogUtils {

  /**
   * Displays a text popup.
   *
   * @param text The text of the popup.
   * @param title The title of the popup.
   * @return The popup entity.
   * @see OkDialog#showOkDialog(String, String, IVoidFunction) showOkDialog
   */
  public static Entity showTextPopup(String text, String title) {
    return showTextPopup(text, title, () -> {});
  }

  /**
   * Displays a text popup. Upon closing the popup, the onFinished function is executed.
   *
   * @param text The text of the popup.
   * @param title The title of the popup.
   * @param onFinished The function to execute when the popup is closed.
   * @return The popup entity.
   * @see OkDialog#showOkDialog(String, String, IVoidFunction) showOkDialog
   */
  public static Entity showTextPopup(String text, String title, IVoidFunction onFinished) {
    // removes newlines and empty spaces and multiple spaces from the title and text
    title = title.replaceAll("\\s+", " ").trim();
    text = text.replaceAll("\\s+", " ").trim();
    return OkDialog.showOkDialog(text, title, onFinished);
  }

  /**
   * Presents a quiz to the player. If the player answers correctly, the next quiz is presented.
   * Otherwise, the player is shown the correct answer. When all quizzes have been solved, the
   * onFinished function is executed.
   *
   * @param quizIterator The iterator of quizzes to present.
   * @param onFinished The function to execute when all quizzes have been solved.
   * @see NewDSLQuizUI#showQuizDialog(Quiz, Function) showQuizDialog
   */
  public static void presentQuiz(Iterator<Quiz> quizIterator, IVoidFunction onFinished) {
    if (!quizIterator.hasNext()) {
      // All quizzes have been correctly solved
      onFinished.execute();
      return;
    }

    Quiz quiz = quizIterator.next();

  }
}
