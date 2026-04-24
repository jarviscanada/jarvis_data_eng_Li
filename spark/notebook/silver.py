from pyspark import pipelines as dp
from pyspark.sql.functions import col, to_date, year, month, dayofmonth, current_timestamp, round

@dp.table
def aapl_silver():
    return(spark.readStream
        .option("ignoreDeletes", "true")
        .table("aapl_bronze")
        .select(
            col("company"),
            to_date(col("date")).alias("trade_date"),
            col("open").cast("double"),
            col("high").cast("double"),
            col("low").cast("double"),
            col("close").cast("double"),
            col("volume").cast("long"),
            year(to_date(col("date"))).alias("year"),
            month(to_date(col("date"))).alias("month"),
            dayofmonth(to_date(col("date"))).alias("day"),
            round(col("high") - col("low"), 2).alias("daily_range"),
            round((col("close") - col("open")) / col("open") * 100, 2).alias("daily_return_pct")
        )
    )

@dp.table
def ibm_silver():
    return(spark.readStream
        .option("ignoreDeletes", "true")
        .table("ibm_bronze")
        .select(
            col("company"),
            to_date(col("date")).alias("trade_date"),
            col("open").cast("double"),
            col("high").cast("double"),
            col("low").cast("double"),
            col("close").cast("double"),
            col("volume").cast("long"),
            year(to_date(col("date"))).alias("year"),
            month(to_date(col("date"))).alias("month"),
            dayofmonth(to_date(col("date"))).alias("day"),
            round(col("high") - col("low"), 2).alias("daily_range"),
            round((col("close") - col("open")) / col("open") * 100, 2).alias("daily_return_pct")
        )
    )

@dp.table
def meta_silver():
    return(spark.readStream
        .option("ignoreDeletes", "true")
        .table("meta_bronze")
        .select(
            col("company"),
            to_date(col("date")).alias("trade_date"),
            col("open").cast("double"),
            col("high").cast("double"),
            col("low").cast("double"),
            col("close").cast("double"),
            col("volume").cast("long"),
            year(to_date(col("date"))).alias("year"),
            month(to_date(col("date"))).alias("month"),
            dayofmonth(to_date(col("date"))).alias("day"),
            round(col("high") - col("low"), 2).alias("daily_range"),
            round((col("close") - col("open")) / col("open") * 100, 2).alias("daily_return_pct")
        )
    )

@dp.table
def googl_silver():
    return(spark.readStream
        .option("ignoreDeletes", "true")
        .table("googl_bronze")
        .select(
            col("company"),
            to_date(col("date")).alias("trade_date"),
            col("open").cast("double"),
            col("high").cast("double"),
            col("low").cast("double"),
            col("close").cast("double"),
            col("volume").cast("long"),
            year(to_date(col("date"))).alias("year"),
            month(to_date(col("date"))).alias("month"),
            dayofmonth(to_date(col("date"))).alias("day"),
            round(col("high") - col("low"), 2).alias("daily_range"),
            round((col("close") - col("open")) / col("open") * 100, 2).alias("daily_return_pct")
        )
    )