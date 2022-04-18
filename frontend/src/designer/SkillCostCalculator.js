const skillCostCalculator = (skill, skillName) => {
    let cost = 0;
    for (let cursor = 1; cursor <= skill; cursor++) {
        if (cursor <= 4) {
            cost += skillName === 'Prowess' ? 1 : 2;
        } else if (cursor <= 8) {
            cost += skillName === 'Prowess' ? 2 : 4;
        } else if (cursor <= 12) {
            cost += skillName === 'Prowess' ? 4 : 7;
        } else if (cursor <= 16) {
            cost += skillName === 'Prowess' ? 7 : 11;
        } else {
            cost += skillName === 'Prowess' ? 11 : 17;
        }
    }
    return cost;
}

export default skillCostCalculator;