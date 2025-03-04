for i in {1..50};
do
    /opt/mssql-tools/bin/sqlcmd -S mssql -U SA -P Passw0rd -d master -i /opt/db_scripts/init.sql
    if [ $? -eq 0 ]
    then
        echo "init.sql completed"
        break
    else
        echo "not ready yet..."
        sleep 1
    fi
done