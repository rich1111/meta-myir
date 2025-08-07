FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " \
    file://webui.tar.gz \
    file://my-app.conf \
    file://nginx.conf \
"

do_install:append() {
    # Overwrite the default nginx.conf with your custom one
    install -m 0644 ${WORKDIR}/nginx.conf ${D}${sysconfdir}/nginx/nginx.conf

    # Create the target directory for our web application
    install -d ${D}${NGINX_WWWDIR}/www

    # Install the web files with read permissions for all (0644)
    install -m 0644 ${WORKDIR}/index.html ${D}${NGINX_WWWDIR}/www/
    cp -r ${WORKDIR}/data_io ${D}${NGINX_WWWDIR}/www/
    
    # Set correct ownership for the web files
    chown -R root:root ${D}${NGINX_WWWDIR}/www

    # Install our custom site configuration
    install -d ${D}${sysconfdir}/nginx/sites-available
    install -m 0644 ${WORKDIR}/my-app.conf ${D}${sysconfdir}/nginx/sites-available/

    # Disable the default site and enable our custom site
    rm -f ${D}${sysconfdir}/nginx/sites-enabled/default_server
    ln -sf ../sites-available/my-app.conf ${D}${sysconfdir}/nginx/sites-enabled/my-app.conf
}


