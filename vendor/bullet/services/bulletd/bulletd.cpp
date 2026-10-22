/*
 * Copyright (C) 2026 BULLET IT Solutions
 * Bullet-OS Core Performance & Security Daemon (bulletd)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 */

#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <thread>
#include <chrono>
#include <sys/socket.h>
#include <sys/un.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <cstring>

#define SOCKET_PATH "/dev/socket/bulletd"
#define LOG_TAG "BulletD"

enum class PerfProfile {
    POWERSAVE,
    BALANCED,
    PERFORMANCE,
    GAMING_BOOST
};

class BulletDaemon {
public:
    BulletDaemon() : currentProfile(PerfProfile::BALANCED), running(true) {}

    void start() {
        std::cout << "[" << LOG_TAG << "] Initializing Bullet-OS Core Daemon v1.0 (BULLET IT Solutions)..." << std::endl;
        initCpuGovernors();
        optimizeZram();
        setupIpcServer();
    }

    void setProfile(PerfProfile profile) {
        currentProfile = profile;
        std::string governor = "schedutil";
        
        switch(profile) {
            case PerfProfile::POWERSAVE:
                governor = "powersave";
                break;
            case PerfProfile::BALANCED:
                governor = "schedutil";
                break;
            case PerfProfile::PERFORMANCE:
            case PerfProfile::GAMING_BOOST:
                governor = "performance";
                break;
        }

        applyGovernor(governor);
        std::cout << "[" << LOG_TAG << "] Switched performance governor to: " << governor << std::endl;
    }

private:
    PerfProfile currentProfile;
    bool running;

    void applyGovernor(const std::string& governor) {
        for (int i = 0; i < 8; ++i) {
            std::string path = "/sys/devices/system/cpu/cpu" + std::to_string(i) + "/cpufreq/scaling_governor";
            std::ofstream file(path);
            if (file.is_open()) {
                file << governor;
                file.close();
            }
        }
    }

    void initCpuGovernors() {
        std::cout << "[" << LOG_TAG << "] Setting up Bullet-OS CPU frequency profiles..." << std::endl;
        setProfile(PerfProfile::BALANCED);
    }

    void optimizeZram() {
        std::cout << "[" << LOG_TAG << "] Configuring zRAM swap compression (lz4/zstd)..." << std::endl;
        std::ofstream compStream("/sys/block/zram0/comp_algorithm");
        if (compStream.is_open()) {
            compStream << "lz4";
            compStream.close();
        }
    }

    void setupIpcServer() {
        int server_fd, client_socket;
        struct sockaddr_un address;

        unlink(SOCKET_PATH);
        server_fd = socket(AF_UNIX, SOCK_STREAM, 0);
        if (server_fd < 0) {
            std::cerr << "[" << LOG_TAG << "] Failed to create IPC socket" << std::endl;
            return;
        }

        memset(&address, 0, sizeof(address));
        address.sun_family = AF_UNIX;
        strncpy(address.sun_path, SOCKET_PATH, sizeof(address.sun_path) - 1);

        if (bind(server_fd, (struct sockaddr*)&address, sizeof(address)) < 0) {
            std::cerr << "[" << LOG_TAG << "] Bind failed on " << SOCKET_PATH << std::endl;
            close(server_fd);
            return;
        }

        chmod(SOCKET_PATH, 0660);
        listen(server_fd, 5);
        std::cout << "[" << LOG_TAG << "] Listening for IPC commands on " << SOCKET_PATH << std::endl;

        char buffer[256];
        while (running) {
            client_socket = accept(server_fd, nullptr, nullptr);
            if (client_socket < 0) continue;

            memset(buffer, 0, sizeof(buffer));
            int bytes = read(client_socket, buffer, sizeof(buffer) - 1);
            if (bytes > 0) {
                std::string cmd(buffer);
                if (cmd.find("PROFILE_BOOST") != std::string::npos) {
                    setProfile(PerfProfile::GAMING_BOOST);
                    write(client_socket, "OK:BOOST_ENABLED\n", 17);
                } else if (cmd.find("PROFILE_BALANCED") != std::string::npos) {
                    setProfile(PerfProfile::BALANCED);
                    write(client_socket, "OK:BALANCED_ENABLED\n", 20);
                } else if (cmd.find("PROFILE_POWERSAVE") != std::string::npos) {
                    setProfile(PerfProfile::POWERSAVE);
                    write(client_socket, "OK:POWERSAVE_ENABLED\n", 21);
                } else if (cmd.find("STATUS") != std::string::npos) {
                    write(client_socket, "BULLET_OS_STATUS:ONLINE\n", 24);
                } else {
                    write(client_socket, "ERR:UNKNOWN_COMMAND\n", 20);
                }
            }
            close(client_socket);
        }
        close(server_fd);
    }
};

int main(int argc, char* argv[]) {
    BulletDaemon daemon;
    daemon.start();
    return 0;
}
