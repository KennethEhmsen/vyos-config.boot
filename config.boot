interfaces {
    ethernet eth0 {
        address dhcp
        description OUTSIDE
        duplex auto
        hw-id 00:15:5d:00:1c:1e
        smp_affinity auto
        speed auto
    }
    ethernet eth1 {
        address 192.168.142.1/24
        description INSIDE
        duplex auto
        hw-id 00:15:5d:00:1c:22
        smp_affinity auto
        speed auto
    }
    loopback lo {
    }
}
nat {
    destination {
        rule 10 {
            description "Port Forward : HTTPto 192.168.142.103"
            destination {
                port 80
            }
            inbound-interface eth0
            protocol tcp
            translation {
                address 192.168.142.101
                port 8888
            }
        }
        rule 12 {
            description Test
            destination {
                port 12345
            }
            inbound-interface eth0
            protocol tcp
            translation {
                address 192.168.142.11
            }
        }
    }
    source {
        rule 1 {
            outbound-interface eth0
            source {
                address 192.168.142.0/24
            }
            translation {
                address masquerade
            }
        }
    }
}
service {
    dhcp-server {
        disabled false
        shared-network-name IRC_NET {
            authoritative disable
            subnet 192.168.142.0/24 {
                default-router 192.168.142.1
                dns-server 9.9.9.9
                domain-name support.local
                lease 86400
                start 192.168.142.100 {
                    stop 192.168.142.200
                }
            }
        }
    }
    ssh {
        port 22
    }
}
system {
    config-management {
        commit-revisions 20
    }
    console {
        device ttyS0 {
            speed 9600
        }
    }
    host-name vyos
    login {
        user vyos {
            authentication {
                encrypted-password *********
                plaintext-password ""
            }
            level admin
        }
    }
    ntp {
        server 0.pool.ntp.org {
        }
        server 1.pool.ntp.org {
        }
        server 2.pool.ntp.org {
        }
    }
    package {
        auto-sync 1
        repository community {
            components main
            distribution helium
            password ""
            url http://packages.vyos.net/vyos
            username ""
        }
    }
    syslog {
        global {
            facility all {
                level notice
            }
            facility protocols {
                level debug
            }
        }
        host 192.168.142.101:6514 {
            facility all {
                level notice
            }
        }
    }
    time-zone UTC
}


/* Warning: Do not remove the following line. */
/* === vyatta-config-version: "cluster@1:config-management@1:conntrack-sync@1:conntrack@1:cron@1:dhcp-relay@1:dhcp-server@4:firewall@5:ipsec@4:nat@4:qos@1:quagga@2:system@6:vrrp@1:wanloadbalance@3:webgui@1:webproxy@1:zone-policy@1" === */
/* Release version: VyOS 1.1.8 */
