/*
 *  Copyright 2019 The TensorFlow Authors. All Rights Reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  =======================================================================
 */

package org.tensorflow.tools.buffer.impl.adapter;

import org.tensorflow.tools.buffer.ByteDataBuffer;
import org.tensorflow.tools.buffer.IntDataBuffer;
import org.tensorflow.tools.buffer.impl.Validator;
import org.tensorflow.tools.buffer.layout.IntDataLayout;

class IntDataBufferAdapter extends AbstractDataBufferAdapter<Integer, IntDataBuffer>
    implements IntDataBuffer {

  @Override
  public int getInt(long index) {
    Validator.getArgs(this, index);
    return layout.readInt(buffer(), index * layout.sizeInBytes());
  }

  @Override
  public IntDataBuffer setInt(int value, long index) {
    Validator.setArgs(this, index);
    layout.writeInt(buffer(), value, index * layout.sizeInBytes());
    return this;
  }

  @Override
  public IntDataBuffer read(int[] dst, int offset, int length) {
    Validator.readArgs(this, dst.length, offset, length);
    for (int i = 0, j = offset; i < length; ++i, ++j) {
      dst[j] = layout.readInt(buffer(), i * layout.sizeInBytes());
    }
    return this;
  }

  @Override
  public IntDataBuffer write(int[] src, int offset, int length) {
    Validator.writeArgs(this, src.length, offset, length);
    for (int i = 0, j = offset; i < length; ++i, ++j) {
      layout.writeInt(buffer(), src[j], i * layout.sizeInBytes());
    }
    return this;
  }

  @Override
  public IntDataBuffer offset(long index) {
    return new IntDataBufferAdapter(buffer().offset(index * layout.sizeInBytes()), layout);
  }

  @Override
  public IntDataBuffer narrow(long size) {
    return new IntDataBufferAdapter(buffer().narrow(size * layout.sizeInBytes()), layout);
  }

  IntDataBufferAdapter(ByteDataBuffer physicalBuffer, IntDataLayout layout) {
    super(physicalBuffer, layout);
    this.layout = layout;
  }

  private IntDataLayout layout;
}
