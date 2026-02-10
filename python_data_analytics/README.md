# Introduction
London Gift Shop (LGS) is a long-established UK-based e-commerce retailer specializing in giftware, with a large share of its customer base comprising wholesalers. 
Despite operating online for over a decade, recent revenue growth has stagnated, prompting the marketing team to seek data-driven strategies to understand customers better 
purchasing behaviour and improve campaign effectiveness.

This project delivers a proof-of-concept analytics solution to support the LGS marketing team. By analyzing historical transaction data, the project aims to look at
insights for customer behaviour, purchasing patterns, cancellations, and geographic trends. These insights can be used to design targeted marketing campaigns such as 
personalized email promotions, event-based offers, and customer segmentation strategies to improve customer retention and acquisition.

The following project uses Python-based data analytics tools in the Jupyter Notebook environment. The following are the packages used in this project:

- pandas
- matplotlib
- numpy
- sqlalchemy
- datetime

# Implementation
## Project Architecture
The LGS online store generates transactional data through its web application, which is stored in a relational database. For this proof-of-concept, the LGS IT team exported 
anonymous historical transaction data into a SQL file and shared it with the Jarvis consulting team.

![my image](./pythonarc.drawio.png)


## Data Analytics and Wrangling

[Retail Data Analytics Wrangling Notebook](./retail_data_analytics_wrangling.ipynb)



3 segments selected for evaluation are "Can't Lose", "Hibernating" and "Champions".

Number of customers for segments:

Can't Lose = 71, Hibernating = 1522, Champions = 852


- Can't Lose Segment;

    - The last shopping date of the customers is on average 353 days before.
    - Customers have made an average of 16 purchases.
    - Customers spent an average of £ 8356.


- Hibernating Segment;

    - The last shopping date of the customers is 481 days before average.
    - Customers made an average of 1 purchases.
    - Customers spent an average of £ 438.


- Champions Segment;

    - The last shopping date of the customers is 30 days before average.
    - Customers made an average of 19 purchases.
    - Customers spent an average of £ 10796.

- Can't Lose Segment;

    - Customers in this segment have not recently made a purchase. For this reason, we need to prepare a discount and gift campaign for this segment. These customers made a large number of purchases when they made purchases before. However, recency values are lower than they should be. The campaign to be implemented for these customers should include both items purchased and recommendations based on previous activities. New and popular products associated with the products that they were interested in can also be included in this campaign. Situations that will cause these customers to stop buying need to be investigated.


- Hibernating Segment;

    - Customers in this segment have not made a purchase for a long time. However, by offering discounts, they may be attracted to another purchase.


- Champions Segment;

    - Customers in this segment are responsible for most of the revenue. Campaigns should be implemented to ensure the continuity of the shopping of these customers.

- Discuss how would you use the data to help LGS to increase their revenue (e.g. design a new marketing strategy with data you provided)

# Improvements
- List three improvements that you want to do if you got more time


