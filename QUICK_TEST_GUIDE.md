# 🧪 Quick Test Reference

## Run Tests

```bash
# Install Maven
sudo apt install maven

# Run all 36 tests
mvn test

# Run specific test
mvn test -Dtest=FarmManagementAppTest#testCropCreation
```

## Test Suite Overview

| Category | Tests | What's Tested |
|----------|-------|---------------|
| **Crop** | 1 | Creation, getters |
| **Field** | 11 | Creation, setters, yield calculations, boundaries |
| **Farm** | 12 | CRUD operations, area management, immutability |
| **Integration** | 6 | Complete workflows, revenue calculations |
| **Edge Cases** | 6 | Null handling, boundaries, invalid states |
| **TOTAL** | **36** | **Complete coverage** |

## Key Tests

✅ Yield calculation with different soil classes (1-6)  
✅ Fertilizer impact (+20%)  
✅ Lime impact (+10%)  
✅ Boundary validation (soil class clamping)  
✅ Null safety  
✅ Total area updates when adding/removing fields  
✅ List immutability  
✅ State transitions (seeded, fertilized, limed)  

## Files

- `src/test/java/FarmManagementAppTest.java` - All 36 tests
- `pom.xml` - Maven config with JUnit 5
- `TEST_DOCUMENTATION.md` - Detailed docs
- `TEST_SUITE_SUMMARY.md` - Complete summary

## Quick Check

```bash
cd /home/xadrx/studia/farm-management-tool
mvn clean test
```

Expected: **Tests run: 36, Failures: 0, Errors: 0** ✅
