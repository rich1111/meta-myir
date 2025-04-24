#!/bin/sh
MAC=$(cat /sys/class/net/eth0/address | tr -d ':')
HOSTNAME="RIO-${MAC}"
hostnamectl set-hostname "${HOSTNAME}"
echo "${HOSTNAME}" > /etc/hostname
