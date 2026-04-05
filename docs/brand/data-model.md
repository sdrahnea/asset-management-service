# Brand — Data Model Specification

**Version**: 1.0 | **Date**: 2026-04-04 | **Table**: `brand`

`Brand` extends `CoreEntity`. `name` is overridden to `brand_id` via `@AttributeOverride`.

## Enums
| Enum | Values |
|------|--------|
| `TrademarkType` | WORDMARK, FIGURATIVE, COMBINED |
| `BrandStatus` | ACTIVE, EXPIRED, PENDING, DISPUTED |
| `SentimentLevel` | POSITIVE, NEUTRAL, NEGATIVE |

## Fields

### Identity
| Field | Column | Type | Required | Unique |
|-------|--------|------|----------|--------|
| `brandId` | `brand_id` | VARCHAR(100) | ✅ | ✅ |
| `brandName` | `brand_name` | VARCHAR(255) | ✅ | |
| `tagline` | `tagline` | VARCHAR(255) | | |
| `description` | `description` | VARCHAR(2000) | | |
| `brandCategory` | `brand_category` | VARCHAR(120) | | |
| `parentCompany` | `parent_company` | VARCHAR(255) | ✅ | |
| `countryOfOrigin` | `country_of_origin` | VARCHAR(100) | | |

### Legal / Trademark
| Field | Column | Type | Required | Unique |
|-------|--------|------|----------|--------|
| `trademarkRegistrationNumber` | `trademark_registration_number` | VARCHAR(100) | | ✅ (when set) |
| `trademarkType` | `trademark_type` | ENUM(20) | | |
| `jurisdictions` | `jurisdictions` | VARCHAR(500) | | |
| `trademarkRegistrationDate` | `trademark_registration_date` | DATE | | |
| `trademarkExpirationDate` | `trademark_expiration_date` | DATE | | ≥ registrationDate |
| `brandStatus` | `brand_status` | ENUM(20) | ✅ | |

### Market Positioning
| Field | Column | Type |
|-------|--------|------|
| `industry` | `industry` | VARCHAR(120) |
| `targetAudience` | `target_audience` | VARCHAR(500) |
| `positioningStatement` | `positioning_statement` | VARCHAR(1000) |
| `valueProposition` | `value_proposition` | VARCHAR(1000) |
| `competitors` | `competitors` | VARCHAR(1000) |
| `geographicMarkets` | `geographic_markets` | VARCHAR(500) |

### Performance Indicators
| Field | Column | Type | Range |
|-------|--------|------|-------|
| `brandEquityScore` | `brand_equity_score` | DECIMAL(7,2) | 0–100 |
| `sentimentLevel` | `sentiment_level` | ENUM(20) | |
| `marketShare` | `market_share` | DECIMAL(7,2) | 0–100 |
| `customerLoyaltyScore` | `customer_loyalty_score` | DECIMAL(7,2) | 0–100 |
| `nps` | `nps` | DECIMAL(7,2) | -100–100 |

### Digital Footprint
| Field | Column | Type |
|-------|--------|------|
| `website` | `website` | VARCHAR(255) |
| `socialMediaAccounts` | `social_media_accounts` | VARCHAR(1000) |
| `followerCount` | `follower_count` | BIGINT |
| `engagementMetrics` | `engagement_metrics` | VARCHAR(500) |
| `seoVisibility` | `seo_visibility` | VARCHAR(500) |

### Financial (Optional)
| Field | Column | Type |
|-------|--------|------|
| `revenueAttributed` | `revenue_attributed` | DECIMAL(22,2) |
| `licensingIncome` | `licensing_income` | DECIMAL(22,2) |
| `brandValuation` | `brand_valuation` | DECIMAL(22,2) |
| `maintenanceCost` | `maintenance_cost` | DECIMAL(22,2) |

### Metadata
| Field | Column | Type | Required |
|-------|--------|------|----------|
| `dataSource` | `data_source` | VARCHAR(100) | ✅ |
| `responsibleAgent` | `responsible_agent` | VARCHAR(100) | |
| `tags` | `tags` | VARCHAR(500) | |
| `notes` | `notes` | VARCHAR(4000) | |

## Unique Constraints
| Name | Column |
|------|--------|
| `uk_brand_brand_id` | `brand_id` |
| `uk_brand_tm_registration_number` | `trademark_registration_number` |

## Normalization
- `brandId` → uppercase, strip whitespace
- All text fields → trim; empty → null

