/* Copyright 2018 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
=======================================================================*/

// This class has been generated, DO NOT EDIT!

package org.tensorflow.op.random;

import org.tensorflow.DataType;
import org.tensorflow.Operand;
import org.tensorflow.Operation;
import org.tensorflow.OperationBuilder;
import org.tensorflow.Output;
import org.tensorflow.op.PrimitiveOp;
import org.tensorflow.op.Scope;
import org.tensorflow.op.annotation.Operator;

/**
 * Outputs deterministic pseudorandom values from a truncated normal distribution.
 * <p>
 * The generated values follow a normal distribution with mean 0 and standard
 * deviation 1, except that values whose magnitude is more than 2 standard
 * deviations from the mean are dropped and re-picked.
 * <p>
 * The outputs are a deterministic function of `shape` and `seed`.
 * 
 * @param <V> data type for {@code output()} output
 */
@Operator(group = "random")
public final class StatelessTruncatedNormal<V extends Number> extends PrimitiveOp implements Operand<V> {
  
  /**
   * Factory method to create a class wrapping a new StatelessTruncatedNormal operation.
   * 
   * @param scope current scope
   * @param shape The shape of the output tensor.
   * @param seed 2 seeds (shape [2]).
   * @param dtype The type of the output.
   * @return a new instance of StatelessTruncatedNormal
   */
  public static <V extends Number, T extends Number, U extends Number> StatelessTruncatedNormal<V> create(Scope scope, Operand<T> shape, Operand<U> seed, Class<V> dtype) {
    OperationBuilder opBuilder = scope.env().opBuilder("StatelessTruncatedNormal", scope.makeOpName("StatelessTruncatedNormal"));
    opBuilder.addInput(shape.asOutput());
    opBuilder.addInput(seed.asOutput());
    opBuilder = scope.applyControlDependencies(opBuilder);
    opBuilder.setAttr("dtype", DataType.fromClass(dtype));
    return new StatelessTruncatedNormal<V>(opBuilder.build());
  }
  
  /**
   * Factory method to create a class wrapping a new StatelessTruncatedNormal operation using default output types.
   * 
   * @param scope current scope
   * @param shape The shape of the output tensor.
   * @param seed 2 seeds (shape [2]).
   * @return a new instance of StatelessTruncatedNormal
   */
  public static <T extends Number, U extends Number> StatelessTruncatedNormal<Float> create(Scope scope, Operand<T> shape, Operand<U> seed) {
    return create(scope, shape, seed, Float.class);
  }
  
  /**
   * Random values with specified shape.
   */
  public Output<V> output() {
    return output;
  }
  
  @Override
  public Output<V> asOutput() {
    return output;
  }
  
  private Output<V> output;
  
  private StatelessTruncatedNormal(Operation operation) {
    super(operation);
    int outputIdx = 0;
    output = operation.output(outputIdx++);
  }
}
