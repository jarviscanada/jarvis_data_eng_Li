# Model Documentation: Credit Risk Classifier

## 1. Model Overview

This document describes a machine learning model built to estimate the probability of loan default for credit applicants.

The model is a supervised binary classifier trained on historical loan application data.

---

## 2. Model Architecture

### Pipeline Structure

Input Data
↓
Preprocessing
├── Numerical Scaling (StandardScaler)
├── Categorical Encoding (OneHotEncoder)
↓
Gradient Boosting Model (XGBoost / Tree Ensemble)
↓
Probability Output (P(Default))


---

## 3. Target Definition

- Target variable: `TARGET`
- Meaning:
  - 1 → Default
  - 0 → Repaid loan

---

## 4. Model Performance

### Test Set Results

| Metric | Value |
|--------|------|
| AUROC | 0.7610 |
| Gini | 0.5219 |
| KS Statistic | 0.3898 |
| AUPRC | 0.2523 |
| F1 Score | 0.3151 |

### Threshold Selection
- Optimal threshold: **0.1537**
- Selected using F1 maximization on validation predictions

---

## 5. Cross-Validation Performance

| Fold | AUROC |
|------|------|
| 1 | 0.7619 |
| 2 | 0.7525 |
| 3 | 0.7546 |
| 4 | 0.7540 |
| 5 | 0.7398 |

### Summary
- Mean AUROC: **0.7526**
- Std Dev: **0.0072**

### Interpretation
The model is highly stable across folds with minimal variance.

---

## 6. Feature Importance (SHAP)

### Top Predictive Features

- EXT_SOURCE_2
- EXT_SOURCE_3
- EXT_SOURCE_1
- AMT_CREDIT
- AMT_GOODS_PRICE
- DAYS_EMPLOYED
- DAYS_BIRTH

### Insights
- External credit bureau scores dominate prediction power
- Financial capacity variables are secondary but important
- Demographic variables have lower but non-zero impact

---

## 7. Explainability (SHAP)

SHAP analysis confirms:

- Lower EXT_SOURCE scores → higher default risk
- Higher credit amount → increased risk
- Shorter employment history → higher risk

The model is interpretable and suitable for regulated environments.

---

## 8. Model Stability (PSI)

### Population Stability Index Results

- All features: PSI < 0.1
- No moderate or high drift detected

### Interpretation

| PSI Range | Meaning |
|----------|--------|
| < 0.1 | Stable |
| 0.1 – 0.25 | Monitor |
| > 0.25 | Significant drift |

### Result
- Model inputs are stable
- No retraining required

---

## 9. Model Monitoring Framework

The following metrics are tracked:

### Predictive Performance
- AUROC (primary metric)
- KS Statistic
- AUPRC

### Stability
- PSI (feature drift)
- Cross-validation variance

### Explainability
- SHAP values
- Feature importance

---

## 10. Adverse Action Logic

When a loan is declined:

The model provides reason codes based on:

- External credit score
- Debt-to-income behavior
- Employment stability
- Credit bureau activity
- Income sufficiency

---

## 11. Risk Segmentation Example

| Segment | Probability | Interpretation |
|--------|------------|---------------|
| Low Risk | ~5% | Approve |
| Medium Risk | ~25% | Manual review |
| High Risk | ~55% | Decline |

---

## 12. Limitations

- Heavy reliance on external credit bureau data
- Limited temporal modeling
- Potential bias in historical lending data

---

## 13. Future Enhancements

- Probability calibration (Platt scaling)
- Fairness / bias testing
- Time-series drift detection
- Model stacking / blending

---

## 14. Conclusion

The model demonstrates:

- Strong predictive power (AUROC ~0.76)
- High stability across folds
- No significant feature drift
- Good interpretability via SHAP

It is suitable for deployment in credit risk decisioning with monitoring in place.