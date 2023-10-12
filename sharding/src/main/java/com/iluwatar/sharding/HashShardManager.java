/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2023 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.sharding;

import lombok.extern.slf4j.Slf4j;

/**
 * ShardManager with hash strategy. The purpose of this strategy is to reduce the
 * chance of hot-spots in the data. It aims to distribute the data across the shards
 * in a way that achieves a balance between the size of each shard and the average
 * load that each shard will encounter.
 */
@Slf4j
public class HashShardManager extends ShardManager {

  @Override
  public int storeData(Data data) {
    var shardId = allocateShard(data);
    var shard = shardMap.get(shardId);
    shard.storeData(data);
    LOGGER.info(data.toString() + " is stored in Shard " + shardId);
    return shardId;
  }

  @Override
  protected int allocateShard(Data data) {
    var shardCount = shardMap.size();
    var hash = data.getKey() % shardCount;
    return hash == 0 ? hash + shardCount : hash;
  }

}
