# Spark/Scala Project
# Introduction
London Gift Shop (LGS) is a long-established UK-based e-commerce retailer specializing in giftware, with a large share of its customer base comprising wholesalers. 
Despite operating online for over a decade, recent revenue growth has stagnated, prompting the marketing team to seek data-driven strategies to understand customers better 
purchasing behaviour and improve campaign effectiveness.

This project delivers a proof-of-concept analytics solution to support the LGS marketing team. By analyzing historical transaction data, the project aims to look at
insights for customer behaviour, purchasing patterns, cancellations, and geographic trends. These insights can be used to design targeted marketing campaigns such as 
personalized email promotions, event-based offers, and customer segmentation strategies to improve customer retention and acquisition.

Because the previous Python-based data analytics was so successful, LGS is investing more in this data engineering project and implementing this data strategy across the company. Since the existing Jupyter notebook and Python cannot handle the large dataset, the team has decided to rebuild the data solution using Apache Spark, which enables processing across a cluster. The project will be run on two separate Spark environments, Zeppelin on Hadoop and Databricks on Microsoft Azure.

The following project implements a combination of Spark with SQL and Python in Zeppelin and Databricks. 

The following are the packages used in this project:

- pandas
- matplotlib
- numpy
- sqlalchemy
- datetime
- pyspark.sql

# Databricks/Zeppelin and Hadoop Implementation
The LGS online store generates transactional data through its web application, which is stored in a relational database. For this proof-of-concept, the LGS IT team exported anonymous historical transaction data into a SQL and JSON file and shared it with the Jarvis consulting team. This data is stored in the Hive Metastore in Databricks and run through PySpark on the cluster, and also stored in HDFS in Zeppelin and run through PySpark on the clusters on GCP.

[View Notebook](./notebook/Retail%20Data%20Analytics%20with%20PySpark.ipynb)

[View JSON Data](./notebook/Spark%20Dataframe%20-%20WDI%20Data%20Analytics.json)

![my image](./spark.drawio.png)

# Future Improvement
-  Optimize Spark tuning by bucketing/partitioning my data more efficiently and caching/persisting my data more.
-  Extend the project so the code can handle Spark streaming and update the data in real-time.
-  Integrate machine learning to predict future values based on the current data given.

# Databricks DLT project
# Introduction

This project focuses on building a scalable analytics pipeline using Databricks Delta Live Tables (DLT) to process daily stock market data sourced from the Alpha Vantage API. Despite the availability of large volumes of financial data, extracting meaningful insights in a reliable and automated manner remains a challenge. This project addresses that gap by designing a structured medallion architecture (bronze → silver → gold) to ingest, clean, and transform stock data for analysis.

The solution delivers a proof-of-concept pipeline that enables analysis of stock price movements, trading volume trends, and short to medium-term performance indicators. By aggregating and visualizing these insights through dashboards, the project supports data-driven decision-making for use cases such as market trend analysis, portfolio monitoring, and investment strategy development. Additionally, the implementation of automated orchestration ensures that the pipeline runs efficiently on a daily schedule, providing up-to-date insights with minimal manual intervention.

# Databricks/Zeppelin and Hadoop Implementation
We first grab our stock data from Vantrage Api, which is then stored along with our different bronze/silver/gold layers in the Unity Catalog on Databricks, which is hosted through Microsoft Azure.

[View Notebook](./notebook/Retail%20Data%20Analytics%20with%20PySpark.ipynb)

[View JSON Data](./notebook/Spark%20Dataframe%20-%20WDI%20Data%20Analytics.json)

![my image](./spark.drawio.png)

# Future Improvement
-  Expand the number of data sources to compare across different outlets.
-  Implementing more financial analytics, such as volatility measures or stock correlations.
-  Integrate machine learning to predict future values of stocks and trading behaviour.
