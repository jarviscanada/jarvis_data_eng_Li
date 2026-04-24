from pyspark.sql.functions import *
from pyspark.sql.window import Window
from pyspark import pipelines as dp
from datetime import datetime, timedelta

# Create a batch view (not streaming) for analysis
@dp.table
def all_silver_batch():
    """
    Batch view of all silver tables (for analytics)
    """
    return (
        spark.table("aapl_silver")
        .union(spark.table("googl_silver"))
        .union(spark.table("meta_silver"))
        .union(spark.table("ibm_silver"))
    )



@dp.table
def gold_table():
    """
    Gold Layer: Price trend analysis over 7, 30, and 90 day periods
    Batch materialized view
    """
    window_7d = Window.partitionBy("company").orderBy("trade_date").rowsBetween(-6, 0)
    window_30d = Window.partitionBy("company").orderBy("trade_date").rowsBetween(-29, 0)
    return (
        spark.table("all_silver_batch")
        .select(
            col("company"),
            col("trade_date"),
            col("close"),
            col("daily_return_pct"),
            col("volume"),
            
            # Price changes
            round(col("close") - lag("close", 7).over(Window.partitionBy("company").orderBy("trade_date")), 2).alias("price_change_7d"),
            round(col("close") - lag("close", 30).over(Window.partitionBy("company").orderBy("trade_date")), 2).alias("price_change_30d"),
            round(col("close") - lag("close", 90).over(Window.partitionBy("company").orderBy("trade_date")), 2).alias("price_change_90d"),
            
            # Price percentage changes
            round((col("close") - lag("close", 7).over(Window.partitionBy("company").orderBy("trade_date"))) / 
                  lag("close", 7).over(Window.partitionBy("company").orderBy("trade_date")) * 100, 2).alias("price_change_pct_7d"),
            round((col("close") - lag("close", 30).over(Window.partitionBy("company").orderBy("trade_date"))) / 
                  lag("close", 30).over(Window.partitionBy("company").orderBy("trade_date")) * 100, 2).alias("price_change_pct_30d"),
            round((col("close") - lag("close", 90).over(Window.partitionBy("company").orderBy("trade_date"))) / 
                  lag("close", 90).over(Window.partitionBy("company").orderBy("trade_date")) * 100, 2).alias("price_change_pct_90d"),
            
            # Volume changes
            (col("volume") - lag("volume", 7).over(Window.partitionBy("company").orderBy("trade_date"))).alias("volume_change_7d"),
            (col("volume") - lag("volume", 30).over(Window.partitionBy("company").orderBy("trade_date"))).alias("volume_change_30d"),
            (col("volume") - lag("volume", 90).over(Window.partitionBy("company").orderBy("trade_date"))).alias("volume_change_90d"),
            
            # Volume percentage changes
            round((col("volume") - lag("volume", 7).over(Window.partitionBy("company").orderBy("trade_date"))) / 
                  lag("volume", 7).over(Window.partitionBy("company").orderBy("trade_date")) * 100, 2).alias("volume_change_pct_7d"),
            round((col("volume") - lag("volume", 30).over(Window.partitionBy("company").orderBy("trade_date"))) / 
                  lag("volume", 30).over(Window.partitionBy("company").orderBy("trade_date")) * 100, 2).alias("volume_change_pct_30d"),
            round((col("volume") - lag("volume", 90).over(Window.partitionBy("company").orderBy("trade_date"))) / 
                  lag("volume", 90).over(Window.partitionBy("company").orderBy("trade_date")) * 100, 2).alias("volume_change_pct_90d"),
            
            # trend indicators
            round(avg("close").over(window_7d), 2).alias("avg_price_7d"),
            round(avg("close").over(window_30d), 2).alias("avg_price_30d"),
            
            when(col("close") > avg("close").over(window_7d), "UP")
            .when(col("close") < avg("close").over(window_7d), "DOWN")
            .otherwise("SAME").alias("trend_7d"),
            
            when(col("close") > avg("close").over(window_30d), "UP")
            .when(col("close") < avg("close").over(window_30d), "DOWN")
            .otherwise("SAME").alias("trend_30d"),
            
            when(col("daily_return_pct") >= 2, "Big Gain")
            .when(col("daily_return_pct") >= 0.5, "Slight Gain")
            .when(col("daily_return_pct") >= -0.5, "Slight Loss")
            .when(col("daily_return_pct") >= -2, "Loss")
            .otherwise("Big Loss").alias("daily_performance"),      
                        rank().over(Window.orderBy(desc("daily_return_pct"))).alias("today_rank"),
            
            # Ranking
            when(rank().over(Window.orderBy(desc("daily_return_pct"))) == 1, "1st")
            .when(rank().over(Window.orderBy(desc("daily_return_pct"))) == 2, "2nd")
            .when(rank().over(Window.orderBy(desc("daily_return_pct"))) == 3, "3rd")
            .otherwise("4th").alias("daily_performance_rank"),  
            )
    )