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

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public final class BundleValueReader {

  private BundleValueReader() {}

  @SuppressWarnings("deprecation")
  @Nullable
  public static Object getValue(@NonNull Bundle bundle, @Nullable String key) {
    return bundle.get(key);
  }

  @Nullable
  public static ArrayList<?> getArrayListValue(@NonNull Bundle bundle, @Nullable String key) {
    return (ArrayList<?>) getValue(bundle, key);
  }

  public static int getIntPreserving(@NonNull Bundle bundle, @Nullable String key) {
    return ObjectUtils.getInt(getValue(bundle, key));
  }

  public static int getIntPreserving(
      @NonNull Bundle bundle, @Nullable String key, int defaultValue) {
    if (!bundle.containsKey(key)) {
      return defaultValue;
    }

    return getIntPreserving(bundle, key);
  }

  public static long getLongPreserving(@NonNull Bundle bundle, @Nullable String key) {
    return ObjectUtils.getLong(getValue(bundle, key));
  }

  public static long getLongPreserving(
      @NonNull Bundle bundle, @Nullable String key, long defaultValue) {
    if (!bundle.containsKey(key)) {
      return defaultValue;
    }

    return getLongPreserving(bundle, key);
  }
}
