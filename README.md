# spark-stringmetric

String similarity functions and phonetic algorithms for Spark.

See [ceja](https://github.com/MrPowers/ceja) if you're using the PySpark API.

## Project Setup

Update your `build.sbt` file to import the libraries.

```
libraryDependencies += "org.apache.commons" % "commons-text" % "1.1"
libraryDependencies += "com.github.mrpowers" %% "spark-stringmetric" % "0.3.0"
```

You can find the spark-daria [Scala 2.11 versions here](https://repo1.maven.org/maven2/com/github/mrpowers/spark-stringmetric.11/) and the [Scala 2.12 versions here](https://repo1.maven.org/maven2/com/github/mrpowers/spark-stringmetric.12/).

You should generally use Scala 2.11 with Spark 2 and Scala 2.12 with Spark 3.

## SimilarityFunctions

* `cosine_distance`
* `fuzzy_score`
* `hamming`
* `jaccard_similarity`
* `jaro_winkler`

How to import the functions.

```scala
import com.github.mrpowers.spark.stringmetric.SimilarityFunctions._
```

Here's an example on how to use the `jaccard_similarity` function.

Suppose we have the following `sourceDF`:

```
+-------+-------+
|  word1|  word2|
+-------+-------+
|  night|  nacht|
|context|contact|
|   null|  nacht|
|   null|   null|
+-------+-------+
```

Let's run the `jaccard_similarity` function.

```scala
val actualDF = sourceDF.withColumn(
  "w1_w2_jaccard",
  jaccard_similarity(col("word1"), col("word2"))
)
```

We can run `actualDF.show()` to view the `w1_w2_jaccard` column that's been appended to the DataFrame.

```
+-------+-------+-------------+
|  word1|  word2|w1_w2_jaccard|
+-------+-------+-------------+
|  night|  nacht|         0.43|
|context|contact|         0.57|
|   null|  nacht|         null|
|   null|   null|         null|
+-------+-------+-------------+
```

## PhoneticAlgorithms

* `double_metaphone`
* `nysiis`
* `refined_soundex`

How to import the functions.

```scala
import com.github.mrpowers.spark.stringmetric.PhoneticAlgorithms._
```

Here's an example on how to use the `refined_soundex` function.

Suppose we have the following `sourceDF`:

```
+-----+
|word1|
+-----+
|night|
|  cat|
| null|
+-----+
```

Let's run the `refined_soundex` function.

```scala
val actualDF = sourceDF.withColumn(
  "word1_refined_soundex",
  refined_soundex(col("word1"))
)
```

We can run `actualDF.show()` to view the `word1_refined_soundex` column that's been appended to the DataFrame.

```
+-----+---------------------+
|word1|word1_refined_soundex|
+-----+---------------------+
|night|               N80406|
|  cat|                 C306|
| null|                 null|
+-----+---------------------+
```

## API Documentation

[Here is the latest API documentation](https://mrpowers.github.io/spark-stringmetric/latest/api/#package).

## Release

1. Create GitHub tag

2. Build documentation with `sbt ghpagesPushSite`

3. Publish JAR

Run `sbt` to open the SBT console.

Run `> ; + publishSigned; sonatypeBundleRelease` to create the JAR files and release them to Maven.  These commands are made available by the [sbt-sonatype](https://github.com/xerial/sbt-sonatype) plugin.

When the release command is run, you'll be prompted to enter your GPG passphrase.

The Sonatype credentials should be stored in the `~/.sbt/sonatype_credentials` file in this format:

```
realm=Sonatype Nexus Repository Manager
host=oss.sonatype.org
user=$USERNAME
password=$PASSWORD
```

## Microsite

The microsite for this project [is available here](https://mrpowers.github.io/spark-stringmetric/).  It's built with the [sbt-microsite](https://github.com/47degrees/sbt-microsites) plugin.

The `sbt publishMicrosite` command creates a new microsite based on the Markdown files stored in the `docs/src/main/mdoc` folder.
