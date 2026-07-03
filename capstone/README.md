# Credit Risk Modeling Project

## Overview
This project builds a machine learning model to predict the probability of loan default using applicant financial and behavioral data. The system is designed for credit risk scoring, decision support, and model monitoring in a financial environment.

The final model is a pipeline-based ensemble model (XGBoost-based classifier) with preprocessing, feature engineering, and probability calibration.

---

## Problem Statement
Predict whether a loan applicant will default (TARGET = 1) using historical credit bureau, income, and behavioral features.

This is a binary classification problem with strong class imbalance.

---

## Dataset
- Source: Preprocessed credit application dataset
- Target variable: `TARGET`
  - 1 = Default
  - 0 = Paid back loan
- Key features include:
  - External credit scores (`EXT_SOURCE_1`, `EXT_SOURCE_2`, `EXT_SOURCE_3`)
  - Income and credit amounts
  - Employment history
  - Demographic and categorical variables

---

## Model Pipeline
The final model is a Scikit-learn pipeline:

- Preprocessing:
  - StandardScaler (numeric features)
  - One-hot encoding (categorical features)

- Model:
  - Gradient boosting classifier (XGBoost / tree-based ensemble)

---

## Performance Metrics (Test Set)

| Metric | Value |
|--------|------|
| AUROC | 0.7610 |
| Gini | 0.5219 |
| KS Statistic | 0.3898 |
| AUPRC | 0.2523 |
| F1 Score | 0.3151 |
| Optimal Threshold | 0.1537 |

### Key Insight
- Model performs well for ranking risk (AUROC ~0.76)
- Low optimal threshold reflects class imbalance
- Strong separation power (KS ~0.39)

---

## Cross-Validation Results

- Mean AUROC: **0.7526**
- Std Dev: **0.0072**
- Range: stable across folds

### Fold Results
- Fold scores: [0.7619, 0.7525, 0.7546, 0.7540, 0.7398]

### Interpretation
- Model is **stable and not overfitting**
- Low variance indicates robust generalization

---

## Feature Importance (SHAP Analysis)

Top predictive features:

1. EXT_SOURCE_2
2. EXT_SOURCE_3
3. AMT_GOODS_PRICE
4. AMT_CREDIT
5. EXT_SOURCE_1
6. DAYS_EMPLOYED
7. CODE_GENDER_M
8. FLAG_OWN_CAR_Y
9. AMT_ANNUITY
10. DAYS_BIRTH

### Key Insight
- External credit bureau scores dominate prediction
- Financial capacity variables are secondary drivers

---

## Model Explainability

SHAP analysis shows:

- External credit scores are strongest predictors
- Higher credit score → lower default probability
- Employment duration and credit amount are strong secondary factors

---

## Stability Monitoring (PSI)

Population Stability Index results:

- All monitored features: PSI < 0.1
- No significant drift detected

### Interpretation
- Model is currently **stable in production conditions**
- No retraining required based on drift analysis

---

## Example Predictions

| Applicant | Default Probability | Decision |
|----------|--------------------|----------|
| High Risk | 54.9% | Declined |
| Borderline | 25.8% | Approved |
| Low Risk | 4.7% | Approved |

---

## Business Use Case

This model can be used for:

- Loan approval automation
- Risk-based pricing
- Credit limit assignment
- Portfolio risk monitoring

---

## Model Monitoring

The system tracks:

- AUROC via cross-validation
- Feature importance drift (PSI)
- SHAP-based explainability
- Threshold optimization (F1-based)

---

## Limitations

- Relies heavily on external credit bureau scores
- Sensitive to missing external data
- Class imbalance affects probability calibration

---

## Future Improvements

- Probability calibration (Platt / Isotonic)
- Fairness analysis across demographic groups
- Time-based drift monitoring
- Ensemble stacking for higher AUROC

---

