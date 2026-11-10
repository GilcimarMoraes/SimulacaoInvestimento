FROM ubuntu:latest
LABEL authors="computador"

ENTRYPOINT ["top", "-b"]
