#
# Copyright (C) 2026 BULLET IT Solutions
# Docker Container for Bullet-OS AOSP Compilation
#

FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive
ENV USER=bullet
ENV UID=1000
ENV GID=1000

# Install build dependencies
RUN apt-get update && apt-get install -y \
    git-core \
    gnupg \
    flex \
    bison \
    build-essential \
    zip \
    curl \
    zlib1g-dev \
    gcc-multilib \
    g++-multilib \
    libc6-dev-i386 \
    lib32ncurses5-dev \
    x11proto-core-dev \
    libx11-dev \
    lib32z1-dev \
    libgl1-mesa-dev \
    libxml2-utils \
    xsltproc \
    unzip \
    fontconfig \
    python3 \
    python3-pip \
    python-is-python3 \
    ccache \
    openjdk-17-jdk \
    bc \
    rsync \
    schedtool \
    sudo \
    && rm -rf /var/lib/apt/lists/*

# Install repo command
RUN curl -o /usr/local/bin/repo https://storage.googleapis.com/git-repo-downloads/repo && \
    chmod a+x /usr/local/bin/repo

# Create non-root build user
RUN groupadd -g $GID $USER && \
    useradd -m -u $UID -g $GID -s /bin/bash $USER && \
    echo "$USER ALL=(ALL) NOPASSWD:ALL" >> /etc/sudoers

USER $USER
WORKDIR /home/bullet/bullet_os

# Configure git identity inside container
RUN git config --global user.name "BULLET IT Solutions Builder" && \
    git config --global user.email "build@bullet-it-solutions.com"

CMD ["/bin/bash"]
