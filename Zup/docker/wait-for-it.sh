#!/usr/bin/env bash
# Use this script to wait for a service to be available.
# Usage: wait-for-it.sh host:port [-t timeout] [--] command args
# Example: wait-for-it.sh mysqldb:3306 -- java -jar myapp.jar

set -e

HOST_PORT="$1"
TIMEOUT=30
COMMAND=""
shift

while [[ "$#" -gt 0 ]]; do
    case "$1" in
        -t) TIMEOUT="$2"; shift 2;;
        --) shift; COMMAND="$@"; break;;
        *) break;;
    esac
done

HOST="${HOST_PORT%:*}"
PORT="${HOST_PORT#*:}"

# Wait for the host to become available
until nc -z "$HOST" "$PORT"; do
    sleep 1
    ((TIMEOUT--))
    if [[ "$TIMEOUT" -le 0 ]]; then
        echo "Timeout waiting for $HOST_PORT"
        exit 1
    fi
done

echo "$HOST_PORT is available"

# Execute the command if provided
if [[ -n "$COMMAND" ]]; then
    exec "$COMMAND"
else
    wait
fi
