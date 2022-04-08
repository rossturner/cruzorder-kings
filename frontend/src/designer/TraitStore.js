import ImpRandom from "../ImpRandom";

let allTraits = [];
const byType = {};
const byInternalName = {};

const TraitStore = {

    initialised: false,

    addTraits: (traits) => {
        if (!TraitStore.initialised) {
            allTraits = allTraits.concat(traits);
            traits.forEach(trait => {
                if (!byType[trait.type]) {
                    byType[trait.type] = [];
                }
                byType[trait.type].push(trait);
                byInternalName[trait.internalName] = trait;
            });
            TraitStore.initialised = true;
        }
    },

    getAll: () => {
        return allTraits;
    },

    getByInternalName: (internalName) => {
        return byInternalName[internalName];
    }
};

export default TraitStore;