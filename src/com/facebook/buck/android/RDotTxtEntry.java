/*
 * Copyright 2014-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.android;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;

/** Represents a row from a symbols file generated by {@code aapt}. */
@VisibleForTesting
class RDotTxtEntry implements Comparable<RDotTxtEntry> {
  @VisibleForTesting final String idType;
  @VisibleForTesting final String type;
  @VisibleForTesting final String name;
  @VisibleForTesting final String originalIdValue;
  @VisibleForTesting final String idValueToWrite;

  public RDotTxtEntry(
      String idType, String type, String name, String originalIdValue,
      String idValueToWrite) {
    this.idType = Preconditions.checkNotNull(idType);
    this.type = Preconditions.checkNotNull(type);
    this.name = Preconditions.checkNotNull(name);
    this.originalIdValue = Preconditions.checkNotNull(originalIdValue);
    this.idValueToWrite = Preconditions.checkNotNull(idValueToWrite);
  }

  /**
   * A collection of Resources should be sorted such that Resources of the same type should be
   * grouped together, and should be alphabetized within that group.
   */
  @Override
  public int compareTo(RDotTxtEntry that) {
    return ComparisonChain.start()
        .compare(this.type, that.type)
        .compare(this.name, that.name)
        .result();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof RDotTxtEntry)) {
      return false;
    }

    RDotTxtEntry that = (RDotTxtEntry) obj;
    return Objects.equal(this.type, that.type) && Objects.equal(this.name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(type, name);
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(RDotTxtEntry.class)
        .add("idType", idType)
        .add("type", type)
        .add("name", name)
        .add("originalIdValue", originalIdValue)
        .add("idValueToWrite", idValueToWrite)
        .toString();
  }
}
