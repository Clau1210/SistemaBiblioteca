if [ -f /etc/secrets/ca.pem ]; then
    rm -f /tmp/aiven-truststore.p12 || exit 1

    keytool -importcert -noprompt \
        -alias aiven-ca \
        -file /etc/secrets/ca.pem \
        -keystore /tmp/aiven-truststore.p12 \
        -storetype PKCS12 \
        -storepass changeit || exit 1
fi
