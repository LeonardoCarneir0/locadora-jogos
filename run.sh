#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"

# Localiza o diretório com javac/java (JAVA_HOME > PATH > SDKMAN > /usr/lib/jvm)
JAVA_BIN=""
if [ -n "$JAVA_HOME" ] && [ -x "$JAVA_HOME/bin/javac" ]; then
    JAVA_BIN="$JAVA_HOME/bin"
elif command -v javac >/dev/null 2>&1; then
    JAVA_BIN="$(dirname "$(command -v javac)")"
elif [ -x "$HOME/.sdkman/candidates/java/current/bin/javac" ]; then
    JAVA_BIN="$HOME/.sdkman/candidates/java/current/bin"
else
    JAVA_BIN="$(find /usr/lib/jvm -name javac -type f 2>/dev/null | head -n1 | xargs dirname 2>/dev/null)"
fi

if [ -z "$JAVA_BIN" ] || [ ! -x "$JAVA_BIN/javac" ]; then
    echo "JDK não encontrado. Instale o Java (JDK 11+) ou defina JAVA_HOME." >&2
    exit 1
fi

echo "Usando JDK: $JAVA_BIN"

mkdir -p out
"$JAVA_BIN/javac" -encoding UTF-8 -d out $(find src -name '*.java')

# Versão em console
"$JAVA_BIN/java" -cp out locadora.Main
