Redact PDF
==========

Primitive command-line script to redact certain words from a PDF.  Potentially useful for anonymising CVs


Usage
-----

```
redactpdf
  -d, --destination  <arg>
  -n, --no-exclude-pronouns
  -s, --source  <arg>
      --help                  Show help message

 trailing arguments:
  redacted-words (required)
```

Building
--------

`sbt assembly`

Then create a bash script somewhere in your $PATH

```
#!/bin/bash

java -jar <path-to-assembled-jar> "$@"
```
