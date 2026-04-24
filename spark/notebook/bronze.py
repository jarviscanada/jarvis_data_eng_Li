from pyspark.sql.functions import col, lit, current_timestamp, to_date
from pyspark import pipelines as dp
from pyspark.sql.functions import col, current_timestamp

@dp.table
def aapl_bronze():
    path = "/Volumes/jarvis_training/bronze/stock_volume/"
    return (
        spark.readStream
            .format("json")
            .option("multiline", "true")
            .option("maxFilesPerTrigger", "1")
            .schema("close DOUBLE, company STRING, date STRING, high DOUBLE, low DOUBLE, open DOUBLE, volume LONG")
            .load(path)
            .filter(col("_metadata.file_name") == "aapl.json")
            .select(
                col("company"),
                col("close"),
                to_date(col("date")).alias("date"),  
                col("high"),
                col("low"),
                col("open"),
                col("volume"),
            )
    )

@dp.table
def ibm_bronze():
    path = "/Volumes/jarvis_training/bronze/stock_volume/"
    return (
        spark.readStream
            .format("json")
            .option("multiline", "true")
            .option("maxFilesPerTrigger", "1")
            .schema("close DOUBLE, company STRING, date STRING, high DOUBLE, low DOUBLE, open DOUBLE, volume LONG")
            .load(path)
            .filter(col("_metadata.file_name") == "ibm.json")
            .select(
                col("company"),
                col("close"),
                to_date(col("date")).alias("date"),  
                col("high"),
                col("low"),
                col("open"),
                col("volume"),
            )
    )

@dp.table
def meta_bronze():
    path = "/Volumes/jarvis_training/bronze/stock_volume/"
    return (
        spark.readStream
            .format("json")
            .option("multiline", "true")
            .option("maxFilesPerTrigger", "1")
            .schema("close DOUBLE, company STRING, date STRING, high DOUBLE, low DOUBLE, open DOUBLE, volume LONG")
            .load(path)
            .filter(col("_metadata.file_name") == "meta.json")
            .select(
                col("company"),
                col("close"),
                to_date(col("date")).alias("date"),  
                col("high"),
                col("low"),
                col("open"),
                col("volume"),
            )
    )

@dp.table
def googl_bronze():
    path = "/Volumes/jarvis_training/bronze/stock_volume/"
    return (
        spark.readStream
            .format("json")
            .option("multiline", "true")
            .option("maxFilesPerTrigger", "1")
            .schema("close DOUBLE, company STRING, date STRING, high DOUBLE, low DOUBLE, open DOUBLE, volume LONG")
            .load(path)
            .filter(col("_metadata.file_name") == "googl.json")
            .select(
                col("company"),
                col("close"),
                to_date(col("date")).alias("date"),  
                col("high"),
                col("low"),
                col("open"),
                col("volume"),
            )
    )