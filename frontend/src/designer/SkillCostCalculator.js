const skillCostCalculator = (skill) => {
    let cost = 0;
    for (let cursor = 1; cursor <= skill; cursor++) {
        if (cursor <= 4) {
            cost += 2;
        } else if (cursor <= 8) {
            cost += 4;
        } else if (cursor <= 12) {
            cost += 7;
        } else if (cursor <= 16) {
            cost += 11;
        } else {
            cost += 17;
        }
    }
    return cost;
}

export default skillCostCalculator;