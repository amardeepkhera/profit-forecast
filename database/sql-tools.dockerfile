FROM mcr.microsoft.com/mssql-tools:latest as mssql


COPY db_scripts/ /opt/db_scripts
RUN chmod +x /opt/db_scripts/initialise.sh
