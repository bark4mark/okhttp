/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package okhttp3.internal.http;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class HttpMethod {
  public static boolean invalidatesCache(String method) {
    return method.equals("POST")
        || method.equals("PATCH")
        || method.equals("PUT")
        || method.equals("DELETE")
        || method.equals("MOVE");     // WebDAV
  }

  public static boolean requiresRequestBody(String method) {
    return method.equals("POST")
        || method.equals("PUT")
        || method.equals("PATCH")
        || method.equals("PROPPATCH") // WebDAV
        || method.equals("REPORT");   // CalDAV/CardDAV (defined in WebDAV Versioning)
  }

  public static boolean permitsRequestBody(String method) {
    return requiresRequestBody(method) || permitsRequestBody.contains(method);
  }

  public static boolean redirectsWithBody(String method) {
    return method.equals("PROPFIND"); // (WebDAV) redirects should also maintain the request body
  }

  public static boolean redirectsToGet(String method) {
    // All requests but PROPFIND should redirect to a GET request.
    return !method.equals("PROPFIND");
  }
  
  public static void addMethodThatPermitsBody(final String method) {
    permitsRequestBody.add(method);
  }

  private HttpMethod() {
  }
  
  private static final Set<String> permitsRequestBody = new HashSet<>(Arrays.asList("OPTIONS", "DELETE", "PROPFIND", "MKCOL", "LOCK"));
}
