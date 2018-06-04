# Object Avalanche
This micro benchmark is a derivative of a [benchmark](https://github.com/excelsior-oss/excelsior-jet-samples/blob/master/objects-avalanche/Main.java) that I believe better measures the JVM Memory Managers allocation scalability.

#Running the test
1. `git clone https://github.com/charliegracie/code-examples.git`
2. `cd code-examples/java/ObjectAvalanche`
3. `export JAVA_HOME=<path to your JVM>`
3. `$JAVA_HOME/bin/javac ObjectAvalanche.java`
4. `./run.sh`

Once you have compiled the source code you can test different JVMs just by modifying the value of `$JAVA_HOME` and running the `run.sh` script again.

