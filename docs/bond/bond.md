For a service that manages **bond information**, the data model needs to capture the financial, legal, and lifecycle attributes of a bond. Bonds are more structured than bank accounts, so the information set is richer and more standardized.

Below is a complete, well‑organized overview of what is typically stored in professional financial systems.

---

# 📘 Core Identification Details
These fields uniquely describe the bond itself.

- **Bond name / title**  
  Example: “Romania Government Bond 2032”.
- **ISIN** (International Securities Identification Number)  
  The global identifier for securities.
- **CUSIP / SEDOL** (if applicable)  
  Used in US/UK markets.
- **Issuer**  
  Government, municipality, corporation, supranational, etc.
- **Issuer country**  
  Important for regulatory and risk classification.
- **Bond type**  
  Government, corporate, municipal, covered, green bond, etc.

---

# 💰 Financial Characteristics
These define how the bond pays and behaves.

- **Face value / par value**  
  The amount repaid at maturity.
- **Coupon rate**  
  Fixed, floating, zero‑coupon.
- **Coupon frequency**  
  Annual, semiannual, quarterly, monthly.
- **Coupon calculation method**  
  Day count convention (30/360, ACT/365, ACT/ACT).
- **Yield** (current yield, yield to maturity, yield to call)  
  Useful for assessment and valuation.
- **Currency**  
  EUR, USD, RON, etc.

---

# 📅 Lifecycle & Maturity Information
These fields describe the timeline of the bond.

- **Issue date**  
  When the bond was created.
- **Maturity date**  
  When principal is repaid.
- **Call dates / put dates**  
  If the bond is callable or putable.
- **Amortization schedule**  
  For bonds that repay principal gradually.
- **Redemption type**  
  Bullet, amortizing, sinking fund.

---

# 🧾 Legal & Structural Attributes
These matter for compliance and risk assessment.

- **Seniority**  
  Senior secured, senior unsecured, subordinated, etc.
- **Collateral** (if any)  
  For secured bonds.
- **Covenants**  
  Restrictions or obligations imposed on the issuer.
- **Prospectus reference**  
  Link or ID to the official issuance document.
- **Rating**  
  From Moody’s, S&P, Fitch, etc.

---

# 📊 Market & Trading Information
Useful for valuation, risk scoring, and analytics.

- **Market price**  
  Clean price, dirty price.
- **Accrued interest**  
  Interest accumulated since last coupon.
- **Trading status**  
  Active, suspended, delisted.
- **Exchange / trading venue**  
  If traded on a regulated market.
- **Liquidity indicators**  
  Bid‑ask spread, volume, turnover.

---

# 🧮 Assessment & Risk Data
Since you’re building an **assessment management service**, these fields are especially relevant.

- **Risk score**  
  Internal rating or scoring model output.
- **Duration**  
  Macaulay duration, modified duration.
- **Convexity**  
  Sensitivity to interest rate changes.
- **Credit risk indicators**  
  Probability of default, loss given default.
- **Market risk indicators**  
  Value at Risk (VaR), stress test results.
- **ESG score** (if applicable)  
  For green or sustainability‑linked bonds.

---

# 🗂️ Optional Metadata
Depending on your business logic:

- **Portfolio or client association**  
  Which entity owns or evaluates the bond.
- **Assessment history**  
  Previous scores, comments, analyst notes.
- **Documents**  
  Term sheets, rating reports, legal opinions.
- **Tags / categories**  
  Useful for filtering and reporting.

---

# 🧩 Example Bond Data Model (High-Level)

| Category | Key Fields |
|---------|------------|
| Identification | ISIN, name, issuer, type |
| Financial | face value, coupon rate, frequency, currency |
| Lifecycle | issue date, maturity date, call/put options |
| Legal | seniority, collateral, covenants |
| Market | price, yield, accrued interest, trading venue |
| Risk | duration, convexity, credit rating, internal score |
| Metadata | documents, tags, assessment history |

