import TraitStore from "./TraitStore";
import React from "react";
import {Image, List, Popup} from "semantic-ui-react";

export const colorizePositiveToGreen = (amount) => {
    const sign = amount > 0 ? '+' : '';
    const color = amount > 0 ? 'green' : amount < 0 ? 'red' : 'black';

    return <span style={{ color }}>{sign}{amount}</span>
}

export const colorizePositiveToRed = (amount) => {
    const sign = amount > 0 ? '+' : '';
    const color = amount > 0 ? 'red' : amount < 0 ? 'green' : 'black';

    return <span style={{ color }}>{sign}{amount}</span>
}

export const buildTraitListItems = (trait) => {
    let items = [];
    trait.skillModifiers.forEach(modifier => {
        items.push(<List.Item key={modifier.skill}>{colorizePositiveToGreen(modifier.modifierAmount)} {modifier.skill}</List.Item>);
    });
    trait.otherModifiers.forEach(other => {
        items.push(<List.Item key={other}>{other}</List.Item>);
    });
    items.push(<List.Item key='cost'><strong>Cost: {colorizePositiveToRed(trait.cost)}</strong></List.Item>)
    return items;
}

const TraitButton = ({internalName, onClick}) => {
    const trait = TraitStore.getByInternalName(internalName);

    let imageUrl = process.env.PUBLIC_URL + '/media/Trait_' + internalName + '.png';
    if (trait.type === 'Education') {
        imageUrl = process.env.PUBLIC_URL + '/media/' + trait.skillModifiers[0].skill + '.png';
    }

    const image = <Image inline bordered width={60} height={60}
                         onClick={onClick} className='trait'
                         src={imageUrl}/>;

    const popupContent = buildTraitListItems(trait);

    return (
        <Popup
            content={<List>{popupContent}</List>}
            header={trait.name}
            trigger={image}
        />
    );
};

export default TraitButton;