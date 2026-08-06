package app.notifee.core.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class NotificationAndroidStyleModelTest {
  private ListeningExecutorService executor;

  @Before
  public void setUp() {
    executor = MoreExecutors.newDirectExecutorService();
  }

  @After
  public void tearDown() {
    executor.shutdownNow();
  }

  @Test
  public void getStyleTask_missingType_currentlyDefaultsToBigPictureStyle() throws Exception {
    NotificationCompat.Style style =
        NotificationAndroidStyleModel.fromBundle(new Bundle()).getStyleTask(executor).get();

    assertTrue(style instanceof NotificationCompat.BigPictureStyle);
  }

  @Test
  public void getStyleTask_integerAndDoubleType_preserveCurrentTypeParsing() throws Exception {
    Bundle bigTextBundle = new Bundle();
    bigTextBundle.putInt("type", 1);
    NotificationCompat.Style bigTextStyle =
        NotificationAndroidStyleModel.fromBundle(bigTextBundle).getStyleTask(executor).get();
    assertTrue(bigTextStyle instanceof NotificationCompat.BigTextStyle);

    Bundle truncatedDoubleBundle = new Bundle();
    truncatedDoubleBundle.putDouble("type", 1.9d);
    NotificationCompat.Style truncatedStyle =
        NotificationAndroidStyleModel.fromBundle(truncatedDoubleBundle)
            .getStyleTask(executor)
            .get();
    assertTrue(truncatedStyle instanceof NotificationCompat.BigTextStyle);
  }

  @Test
  public void getStyleTask_wrongType_currentlyThrowsClassCastExceptionSynchronously() {
    Bundle bundle = new Bundle();
    bundle.putString("type", "1");

    assertThrows(
        ClassCastException.class,
        () -> NotificationAndroidStyleModel.fromBundle(bundle).getStyleTask(executor));
  }

  @Test
  public void messagingStyle_withMessages_preservesParcelableArrayListAndTimestampParsing()
      throws Exception {
    Bundle bundle = baseMessagingStyleBundle();
    ArrayList<Bundle> messages = new ArrayList<>();
    messages.add(messageBundle("first", 123L, personBundle("Alice")));
    messages.add(messageBundle("second", 456.9d, null));
    putRawParcelableArrayList(bundle, "messages", messages);

    NotificationCompat.Style style =
        NotificationAndroidStyleModel.fromBundle(bundle).getStyleTask(executor).get();

    assertTrue(style instanceof NotificationCompat.MessagingStyle);
    NotificationCompat.MessagingStyle messagingStyle = (NotificationCompat.MessagingStyle) style;
    List<NotificationCompat.MessagingStyle.Message> parsedMessages = messagingStyle.getMessages();

    assertEquals(2, parsedMessages.size());
    assertEquals("first", parsedMessages.get(0).getText().toString());
    assertEquals(123L, parsedMessages.get(0).getTimestamp());
    assertNotNull(parsedMessages.get(0).getPerson());
    assertEquals("Alice", parsedMessages.get(0).getPerson().getName().toString());
    assertEquals("second", parsedMessages.get(1).getText().toString());
    assertEquals(456L, parsedMessages.get(1).getTimestamp());
  }

  @Test
  public void messagingStyle_missingMessages_currentlyFailsInsideFuture() {
    Bundle bundle = baseMessagingStyleBundle();

    ListenableFuture<NotificationCompat.Style> future =
        NotificationAndroidStyleModel.fromBundle(bundle).getStyleTask(executor);

    ExecutionException exception = assertThrows(ExecutionException.class, future::get);
    assertTrue(exception.getCause() instanceof NullPointerException);
  }

  @Test
  public void messagingStyle_integerTimestamp_currentlyFailsInsideFuture() {
    Bundle bundle = baseMessagingStyleBundle();
    ArrayList<Bundle> messages = new ArrayList<>();
    Bundle message = new Bundle();
    message.putString("text", "integer timestamp");
    message.putInt("timestamp", 123);
    messages.add(message);
    putRawParcelableArrayList(bundle, "messages", messages);

    ListenableFuture<NotificationCompat.Style> future =
        NotificationAndroidStyleModel.fromBundle(bundle).getStyleTask(executor);

    ExecutionException exception = assertThrows(ExecutionException.class, future::get);
    assertTrue(exception.getCause() instanceof ClassCastException);
  }

  private static Bundle baseMessagingStyleBundle() {
    Bundle bundle = new Bundle();
    bundle.putInt("type", 3);
    bundle.putBundle("person", personBundle("Me"));
    return bundle;
  }

  private static Bundle personBundle(String name) {
    Bundle person = new Bundle();
    person.putString("name", name);
    return person;
  }

  private static Bundle messageBundle(String text, Object timestamp, Bundle person) {
    Bundle message = new Bundle();
    message.putString("text", text);
    if (timestamp instanceof Long) {
      message.putLong("timestamp", (Long) timestamp);
    } else if (timestamp instanceof Double) {
      message.putDouble("timestamp", (Double) timestamp);
    }
    if (person != null) {
      message.putBundle("person", person);
    }
    return message;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  private static void putRawParcelableArrayList(Bundle bundle, String key, ArrayList<?> arrayList) {
    bundle.putParcelableArrayList(key, (ArrayList) arrayList);
  }
}
