#!/bin/sh
# Maven Wrapper script
set -e

MAVEN_PROJECTDIR="."
WRAPPER_JAR=".mvn/wrapper/maven-wrapper.jar"
WRAPPER_LAUNCHER="org.apache.maven.wrapper.MavenWrapperMain"

if [ -r "../.mvn/wrapper/maven-wrapper.jar" ]; then
  MAVEN_PROJECTDIR=..
fi

if [ -z "$MAVEN_SKIP_RC" ]; then
  if [ -f /usr/local/etc/mavenrc ]; then
    . /usr/local/etc/mavenrc
  fi
  if [ -f /etc/mavenrc ]; then
    . /etc/mavenrc
  fi
  if [ -f "$HOME/.mavenrc" ]; then
    . "$HOME/.mavenrc"
  fi
fi

if [ ! -r "$MAVEN_PROJECTDIR/$WRAPPER_JAR" ]; then
  if [ -z "$MAVEN_WRAPPER_URL" ]; then
    if [ -r "$MAVEN_PROJECTDIR/.mvn/wrapper/maven-wrapper.properties" ]; then
      MAVEN_WRAPPER_URL=$(grep wrapperUrl "$MAVEN_PROJECTDIR/.mvn/wrapper/maven-wrapper.properties" | cut -d'=' -f2)
    fi
  fi
  if [ -z "$MAVEN_WRAPPER_URL" ]; then
    echo "Error: Could not find maven-wrapper.properties"
    exit 1
  fi
  echo "Downloading Maven Wrapper..."
  mkdir -p "$MAVEN_PROJECTDIR/.mvn/wrapper"
  if command -v curl >/dev/null 2>&1; then
    curl -sL -o "$MAVEN_PROJECTDIR/$WRAPPER_JAR" "$MAVEN_WRAPPER_URL"
  elif command -v wget >/dev/null 2>&1; then
    wget -q -O "$MAVEN_PROJECTDIR/$WRAPPER_JAR" "$MAVEN_WRAPPER_URL"
  else
    echo "Error: curl or wget required to download Maven Wrapper"
    exit 1
  fi
fi

if [ -z "$JAVA_HOME" ]; then
  JAVA_CMD="java"
else
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

exec "$JAVA_CMD" \
  -Dmaven.multiModuleProjectDirectory="$MAVEN_PROJECTDIR" \
  -classpath "$MAVEN_PROJECTDIR/$WRAPPER_JAR" \
  org.apache.maven.wrapper.MavenWrapperMain "$@"
