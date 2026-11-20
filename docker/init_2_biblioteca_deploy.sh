domain_config="${PAYARA_DIR}/glassfish/domains/${DOMAIN_NAME}/config/domain.xml"

if [ -f "$domain_config" ] && [ -f "$POSTBOOT_COMMANDS_FINAL" ]; then
    if grep -Eq '<application[[:space:]][^>]*name="SistemaBiblioteca"' "$domain_config"; then
        sed -i '\|^deploy .*SistemaBiblioteca\.war$|d' "$POSTBOOT_COMMANDS_FINAL" || exit 1
        echo "SistemaBiblioteca already registered"
    fi
fi
