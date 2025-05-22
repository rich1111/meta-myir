#!/bin/sh
MAC=$(cat /sys/class/net/eth0/address | tr -d ':')
HOSTNAME="RIO-${MAC}"
hostnamectl set-hostname "${HOSTNAME}"
echo "${HOSTNAME}" > /etc/hostname

SSID="RIO-${MAC}"
sed -e "s#{{SSID}}#$SSID#" \
    /etc/myir_hostapd.conf.tpl > /etc/myir_hostapd.conf
    
