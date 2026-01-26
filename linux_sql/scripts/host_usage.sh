# Assign CLI arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# If less than 5 parameters produce error message and exit
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

# Save computer specs to variables
hostname=$(hostname -f)
memory_free=$(vmstat --unit M | tail -1 | awk -v col="4" '{print $col}'| xargs)
cpu_idle=$(vmstat --unit M | tail -1 | awk -v col="15" '{print $col}'| xargs)
cpu_kernel=$(vmstat --unit M | tail -1 | awk -v col="14" '{print $col}'| xargs)
disk_io=$(vmstat --unit M -d | tail -1 | awk -v col="10" '{print $col}'| xargs)
disk_available=$(df -BM / | awk 'NR==2 {gsub("M",""); print $4}'| xargs)
timestamp=$(date +"%Y-%m-%d %H:%M:%S"| xargs)

# Insert data into host_info table
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";
insert_stmt="INSERT INTO host_usage(timestamp, host_id, memory_free, cpu_idle, cpu_kernel, disk_io, disk_available)
            Values('$timestamp',$host_id,$memory_free, $cpu_idle, $cpu_kernel, $disk_io, $disk_available);"

export PGPASSWORD=$psql_password
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?