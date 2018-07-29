/*
 * Copyright 2018 Tadej Slamic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.tadej.versioning.cmd

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.OutputStream
import java.nio.charset.Charset

class StringConverterTest {
    @Test
    fun adapt_withPlainString_returnsSameString() {
        val adapter = StringConverter()
        val string = "I feel the need - the need for speed!"

        val stream = string.toOutputStream(adapter.charset)
        val result = adapter.convert(stream)

        assertThat(result).isEqualTo(string)
    }

    @Test
    fun adapt_withTrailingWhitespaceString_returnsTrimmedString() {
        val adapter = StringConverter()
        val string = "I'm gonna make him an offer he can't refuse."
        val trailing = "\t\n\r $string \n\n\r\t"

        val stream = trailing.toOutputStream(adapter.charset)
        val result = adapter.convert(stream)

        assertThat(result).isEqualTo(string)
    }

    private fun String.toOutputStream(charset: Charset): OutputStream {
        val stream = ByteArrayOutputStream(length)
        val bytes = toByteArray(charset)
        stream.write(bytes)
        return stream
    }
}
