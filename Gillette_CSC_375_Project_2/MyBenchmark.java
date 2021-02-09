/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.flights;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(value = 2)
@Measurement(iterations = 5, time =10)
@Warmup(iterations = 2, time = 2)
@Threads(64)
public class MyBenchmark {

    private static ConcurrentHashMap flightMap = new ConcurrentHashMap(200);
    //private static ConcurrentHashTable flightMap = new ConcurrentHashTable(200);

    @State(Scope.Thread)
    public static class MyState{
        Flight flight;

        @Setup(Level.Iteration)
        public void setup(){
            try {
                autoPopulate(20);
                flight = getRandomFlight();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @TearDown(Level.Iteration)
        public void teardown(){
            flight.setPassengers(0);
        }
    }

    private static Flight getRandomFlight() throws InterruptedException {
        Flight f = null;
        while(f == null) {
            int r = ThreadLocalRandom.current().nextInt(1, 10000 + 1);
            String id = "F" + Integer.toString(r);
            f = (Flight) flightMap.get(id);
        }
        return f;
    }

    //END SIM USER


    //Automatically populate the flightsMap with this data.
    public static void autoPopulate(int n) throws InterruptedException {
        flightMap.put("F2853", new Flight("F2853", 120));
        flightMap.put("F7233", new Flight("F7233", 550));
        flightMap.put("F2118", new Flight("F2118", 125));
        flightMap.put("F7846", new Flight( "F7846", 230));
        flightMap.put("F2253", new Flight( "F2253", 420));
        flightMap.put("F3953", new Flight( "F3953", 500));
        flightMap.put("F6153", new Flight( "F6153", 550));
        flightMap.put("F1851", new Flight( "F1851", 45));
        flightMap.put("F0843", new Flight( "F0843", 80));
        flightMap.put("F614", new Flight( "F614", 150));

        flightMap.put("F1234", new Flight( "F1234", 120));
        flightMap.put("F2345", new Flight( "F2345", 550));
        flightMap.put("F3456", new Flight( "F3456", 125));
        flightMap.put("F4567", new Flight( "F4567", 230));
        flightMap.put("F5678", new Flight( "F5678", 420));
        flightMap.put("F6789", new Flight( "F6789", 500));
        flightMap.put("F7890", new Flight( "F7890", 550));
        flightMap.put("F8901", new Flight( "F8901", 45));
        flightMap.put("F9012", new Flight( "F9012", 80));
        flightMap.put("F1020", new Flight( "F1020", 150));

        flightMap.put("F123", new Flight( "F123", 120 ));
        flightMap.put("F234", new Flight( "F234", 550 ));
        flightMap.put("F345", new Flight( "F345", 125 ));
        flightMap.put("F456", new Flight( "F456", 230 ));
        flightMap.put("F567", new Flight( "F567", 420 ));
        flightMap.put("F678", new Flight( "F678", 500 ));
        flightMap.put("F789", new Flight( "F789", 550 ));
        flightMap.put("F890", new Flight( "F890", 45 ));
        flightMap.put("F901", new Flight( "F901", 80 ));
        flightMap.put("F101", new Flight( "F101", 150 ));

        flightMap.put("F134", new Flight( "F134", 120 ));
        flightMap.put("F145", new Flight( "F145", 550 ));
        flightMap.put("F156", new Flight( "F156", 125 ));
        flightMap.put("F654", new Flight( "F654", 230 ));
        flightMap.put("F893", new Flight( "F893", 420 ));
        flightMap.put("F1575", new Flight( "F1575", 500 ));
        flightMap.put("F6824", new Flight( "F6824", 550 ));
        flightMap.put("F4793", new Flight( "F4793", 45 ));
        flightMap.put("F7138", new Flight( "F7138", 80));
        flightMap.put("F845", new Flight( "F845", 150));

        flightMap.put("F9365", new Flight( "F9365", 120));
        flightMap.put("F9375", new Flight( "F9375", 550));
        flightMap.put("F6254", new Flight( "F6254", 125));
        flightMap.put("F7427", new Flight( "F7427", 230));
        flightMap.put("F6104", new Flight( "F6104", 420));
        flightMap.put("F1465", new Flight( "F1465", 500));
        flightMap.put("F7142", new Flight( "F7142", 550));
        flightMap.put("F9099", new Flight( "F9099", 45));
        flightMap.put("F5523", new Flight( "F5523", 80));
        flightMap.put("F8826", new Flight( "F8826", 150));
    }


    @Benchmark
    public void testMethod(MyState myState) {

            int op = ThreadLocalRandom.current().nextInt(10);

            if (op <= 2) {
                //Write
                try {
                    myState.flight = getRandomFlight();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int p = myState.flight.getPassengers();
                p = p + 1;//Just to make sure it wont be ignored by the JVM
                myState.flight.setPassengers(p);
            } else {
                //read
                try {
                    myState.flight = getRandomFlight();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int p = myState.flight.getPassengers();
                p = p + 1;//Just to make sure it wont be ignored by the JVM

            }

        }



}
