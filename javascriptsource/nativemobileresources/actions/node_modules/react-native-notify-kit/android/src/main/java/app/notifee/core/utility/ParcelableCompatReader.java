package app.notifee.core.utility;

/*
 * Copyright (c) 2016-present Invertase Limited & Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this library except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public final class ParcelableCompatReader {

  private ParcelableCompatReader() {}

  @Nullable
  public static <T extends Parcelable> T getParcelable(
      @NonNull Bundle bundle, @Nullable String key, @NonNull Class<T> clazz) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      return bundle.getParcelable(key, clazz);
    }

    return getParcelableLegacy(bundle, key);
  }

  @Nullable
  public static <T extends Parcelable> ArrayList<T> getParcelableArrayList(
      @NonNull Bundle bundle, @Nullable String key, @NonNull Class<T> clazz) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      return bundle.getParcelableArrayList(key, clazz);
    }

    return getParcelableArrayListLegacy(bundle, key);
  }

  @SuppressWarnings({"deprecation", "unchecked"})
  @Nullable
  private static <T extends Parcelable> T getParcelableLegacy(
      @NonNull Bundle bundle, @Nullable String key) {
    return bundle.getParcelable(key);
  }

  @SuppressWarnings({"deprecation", "unchecked"})
  @Nullable
  private static <T extends Parcelable> ArrayList<T> getParcelableArrayListLegacy(
      @NonNull Bundle bundle, @Nullable String key) {
    return bundle.getParcelableArrayList(key);
  }
}
