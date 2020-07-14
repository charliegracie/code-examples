### TypeCheck
TypeCheck was created to investigate an inlining issue that was found when moving from JDK8 to JDK 11. The original code created objects with the same pattern as TypeCheck.java in the handleImplX() methods. With the move to JDK 11 the InnerImplX.getField() is no longer devirtualized and inlined into Handler.doIt().

To reproduce this issue you can run TypeCheck with the following command line using a JDK 11+ fastdebug build:

`java -XX:CompileCommand=compileonly,*::handleImpl* -XX:+PrintOptoInlining -cp . TypeCheck`

You will see output like the following:

```
                              @ 8   InnerImpl2::<init> (5 bytes)   inline (hot)
                                @ 1   Inner::<init> (11 bytes)   inline (hot)
                                  @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 11   Handler::<init> (10 bytes)   inline (hot)
                                @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 16   Handler::doIt (8 bytes)   inline (hot)

                              @ 8   InnerImpl1::<init> (5 bytes)   inline (hot)
                                @ 1   Inner::<init> (11 bytes)   inline (hot)
                                  @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 11   Handler::<init> (10 bytes)   inline (hot)
                                @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 16   Handler::doIt (8 bytes)   inline (hot)

                              @ 8   InnerImpl3::<init> (5 bytes)   inline (hot)
                                @ 1   Inner::<init> (11 bytes)   inline (hot)
                                  @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 11   Handler::<init> (10 bytes)   inline (hot)
                                @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 16   Handler::doIt (8 bytes)   inline (hot)
```

After some quick debugging I released that if I run JDK 11 with `-XX:+UseParallelGC` that everything gets inlined exactly like JDK pe of t8. It is in fact the switch to G1GC as the default in JDK 9 that caused this performance difference. Here is the output if you run with `-XX:+UseParallelGC`:

```
                              @ 8   InnerImpl3::<init> (5 bytes)   inline (hot)
                                @ 1   Inner::<init> (11 bytes)   inline (hot)
                                  @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 11   Handler::<init> (10 bytes)   inline (hot)
                                @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 16   Handler::doIt (8 bytes)   inline (hot)
                                @ 4   InnerImpl3::getField (8 bytes)   inline (hot)

                              @ 8   InnerImpl2::<init> (5 bytes)   inline (hot)
                                @ 1   Inner::<init> (11 bytes)   inline (hot)
                                  @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 11   Handler::<init> (10 bytes)   inline (hot)
                                @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 16   Handler::doIt (8 bytes)   inline (hot)
                                @ 4   InnerImpl2::getField (8 bytes)   inline (hot)

                              @ 8   InnerImpl1::<init> (5 bytes)   inline (hot)
                                @ 1   Inner::<init> (11 bytes)   inline (hot)
                                  @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 11   Handler::<init> (10 bytes)   inline (hot)
                                @ 1   java.lang.Object::<init> (1 bytes)   inline (hot)
                              @ 16   Handler::doIt (8 bytes)   inline (hot)
                                @ 4   InnerImpl1::getField (8 bytes)   inline (hot)
```

When running with G1GC the post barrier has an `Op_MemBarVolatile` which I believe is blocking optimizations that normally allow the value stored in the constructor to be seen by the read following since nothing else has could have been stored. Without seeing the previously stored value the type of the field read is limited to the type of the field which is the super class Inner. When the previously stored value can be seen the proper subclass InnerImplX can be seen and allows the call to getField to be devirtualized and inlined.

Also, I noticed that if the code in TypeCheck.java is re-written like the code in TypeCheck2.java inlining happens for both G1GC and ParallelGC.