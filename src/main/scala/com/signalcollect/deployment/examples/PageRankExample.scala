/*
 *  @author Philip Stutz
 *
 *  Copyright 2014 University of Zurich
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.signalcollect.deployment.examples

import com.signalcollect.GraphBuilder
import com.signalcollect.deployment.DeployableAlgorithm
import com.signalcollect.examples.PageRankEdge
import com.signalcollect.examples.PageRankVertex

import akka.actor.ActorRef

class PageRankExample extends DeployableAlgorithm {
  def execute(parameters: Map[String, String], nodeActors: Array[ActorRef]) {
    println(s"Received parameters $parameters")
    val graph = GraphBuilder.withPreallocatedNodes(nodeActors).build
    graph.addVertex(new PageRankVertex(1))
    graph.addVertex(new PageRankVertex(2))
    graph.addVertex(new PageRankVertex(3))
    graph.addEdge(1, new PageRankEdge(2))
    graph.addEdge(2, new PageRankEdge(1))
    graph.addEdge(2, new PageRankEdge(3))
    graph.addEdge(3, new PageRankEdge(2))
    println("Graph has been built, awaiting idle ...")
    graph.awaitIdle
    println("Executing computation ...")
    val stats = graph.execute
    println(stats)
    graph.shutdown
  }
}
