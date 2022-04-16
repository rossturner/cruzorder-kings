import {Slider} from "react-semantic-ui-range";
import React, {useEffect, useMemo, useState} from "react";
import {
    Button,
    Card,
    Checkbox,
    Container,
    Form,
    Grid,
    Header,
    Icon,
    Input,
    List,
    Loader,
    Message,
    Progress,
    Segment
} from "semantic-ui-react";
import cultureMapping from "./CultureMapping";
import ageCostCalculator from "./AgeCostCalculator";
import TraitButton, {buildTraitListItems} from "./TraitButton";
import TraitStore from "./TraitStore";
import skillCostCalculator from "./SkillCostCalculator";
import SkillControl from "./SkillControl";
import axios from "axios";
import {useHistory} from "react-router-dom";

const CharacterDesignerPage = ({loggedInPlayer}) => {
    let history = useHistory();

    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);
    const [errorText, setErrorText] = useState(false);

    const [dynastyNamePrefix, setDynastyNamePrefix] = useState('');
    const [dynastyName, setDynastyName] = useState('');
    const [dynastyMotto, setDynastyMotto] = useState('');
    const [dynastyCoa, setDynastyCoa] = useState('');
    const [copyCoa, setCopyCoa] = useState(false);

    const [primaryCharacterName, setPrimaryCharacterName] = useState('');
    const [primaryCharacterGender, setPrimaryCharacterGender] = useState('male');
    const [sexualOrientation, setSexualOrientation] = useState('Heterosexual');

    const [cultureGroup, setCultureGroup] = useState('North Germanic');
    const [culture, setCulture] = useState('Norse');
    const [primaryCharacterDna, setPrimaryCharacterDna] = useState('');

    const [designerPoints, setDesignerPoints] = useState(0);
    const [primaryCharacterAge, setPrimaryCharacterAge] = useState(25);

    const [educationTraitName, setEducationTraitName] = useState('education_diplomacy_1');
    const educationTraits = TraitStore.getAll().filter(t => t.type === 'Education');
    const educationTrait = TraitStore.getByInternalName(educationTraitName);
    const [showEducations, setShowEducations] = useState(false);

    const [selectedTraits, setSelectedTraits] = useState([]);
    const [showMoreTraits, setShowMoreTraits] = useState(false);
    let excludedTraits = [];
    selectedTraits.forEach(selected => {
        excludedTraits = excludedTraits.concat(TraitStore.getByInternalName(selected).exclusiveWith);
    });
    const availableTraits = TraitStore.getAll().filter(t => t.type !== 'Education')
        .filter(t => !selectedTraits.includes(t.internalName) && !excludedTraits.includes(t.internalName));
    const availableTraitsByType = {};
    availableTraits.forEach(t => {
        if (!availableTraitsByType[t.type]) {
            availableTraitsByType[t.type] = [];
        }
        availableTraitsByType[t.type].push(t);
    });
    const traitSections = [];
    for (const [traitType, traits] of Object.entries(availableTraitsByType)) {
        traitSections.push(<Segment>
            <h4>{traitType}</h4>
            {traitType === 'Congenital' && <p>Congenital traits might be passed down to your children.</p>}
            {traitType === 'Infamous' &&
            <p>Infamous traits are likely to be shunned or even a crime (giving your liege an excuse to imprison you and
                revoke your titles) depending on religion.</p>}
            {traits.map(t => <TraitButton key={t.internalName} internalName={t.internalName} onClick={() =>
                setSelectedTraits(selectedTraits.concat([t.internalName]))
            }/>)}
        </Segment>);
    }

    // TODO add in selected territory as first option when editing
    const [territoryOptions, setTerritoryOptions] = useState([]);
    const [selectedTerritory, setSelectedTerritory] = useState('');

    useMemo(() => {
        axios.get("/api/territory/available")
            .then(response => {
                setTerritoryOptions(response.data);
            })
            .finally(() => setLoading(false));
    }, []);

    const [baseSkills, setBaseSkills] = useState({
        'Diplomacy': 5,
        'Intrigue': 5,
        'Martial': 5,
        'Learning': 5,
        'Stewardship': 5,
        'Prowess': 5,
    });

    const [skills, setSkills] = useState({
        'Diplomacy': 0,
        'Intrigue': 0,
        'Martial': 0,
        'Learning': 0,
        'Stewardship': 0,
        'Prowess': 0,
    });

    const [married, setMarried] = useState(false);
    const [spouseName, setSpouseName] = useState('');
    const [numChildren, setNumChildren] = useState(0);
    const [children, setChildren] = useState([]);
    const [childAges, setChildAges] = useState('young');

    const childControls = children.map((child, index) => {
        const clone = Object.assign({}, child);

        const updateChildrenState = () => {
            const newChildren = [];
            for (let cursor = 0; cursor < children.length; cursor++) {
                if (cursor === index) {
                    newChildren.push(clone);
                } else {
                    newChildren.push(children[cursor]);
                }
            }
            setChildren(newChildren);
        }

        return <Segment key={index}>
            <h4>Child {index + 1}</h4>

            <Input label='Name' value={child.name}
                   onChange={(event, data) => {
                       clone.name = data.value;
                       updateChildrenState();
                   }} fluid={true}
                   placeholder="Child's given name (first name)"/>

            <Form.Group inline>
                <label>Gender</label>
                <Form.Radio
                    label='Male'
                    value='male'
                    checked={child.isFemale === false}
                    onChange={() => {clone.isFemale = false; updateChildrenState();}}
                />
                <Form.Radio
                    label='Female'
                    value='female'
                    checked={child.isFemale === true}
                    onChange={() => { clone.isFemale = true; updateChildrenState();}}
                />
            </Form.Group>
        </Segment>
    });

    useEffect(() => {
        const calculated = Object.assign({}, baseSkills);

        let characterTraits = [educationTrait];
        selectedTraits.forEach(selected => characterTraits.push(TraitStore.getByInternalName(selected)));

        Object.keys(calculated).forEach(skill => {
            characterTraits.forEach(trait => {
                trait.skillModifiers.forEach(modifier => {
                    if (modifier.skill === skill) {
                        calculated[skill] = calculated[skill] + modifier.modifierAmount;
                    }
                });
            });
        });

        setSkills(calculated);
    }, [baseSkills, educationTrait, selectedTraits]);


    const maxChildren = Math.max(0, primaryCharacterAge - 16);

    const cultureGroupOptions = Object.keys(cultureMapping).map(cultureGroup => {
        return {
            key: cultureGroup,
            text: cultureGroup,
            value: cultureGroup
        }
    });
    let cultureOptions = cultureGroup === '' ? [] : cultureMapping[cultureGroup].map(culture => {
        return {
            key: culture,
            text: culture,
            value: culture
        }
    });
    let territoryOptionItems = territoryOptions.map(t => {
        return {
            key: t.territoryId,
            text: t.displayName,
            value: t.territoryId,
        }
    });

    useEffect(() => {
        if (numChildren > maxChildren) {
            setNumChildren(maxChildren);
        }
    }, [primaryCharacterAge]);
    useEffect(() => {
        while (numChildren > children.length) {
            children.push({
                name: '',
                isFemale: false
            });
        }
        while (children.length > numChildren) {
            children.pop();
        }
    }, [numChildren]);

    useEffect(() => {
        let pointsSpent = 0;
        pointsSpent += ageCostCalculator(primaryCharacterAge);
        pointsSpent += educationTrait.cost;
        selectedTraits.forEach(t => {
            pointsSpent += TraitStore.getByInternalName(t).cost;
        });
        Object.values(baseSkills).forEach(skillValue => {
            pointsSpent += skillCostCalculator(skillValue);
        });
        pointsSpent += (numChildren * 10);
        setDesignerPoints(pointsSpent);
    }, [primaryCharacterAge, educationTraitName, selectedTraits, baseSkills, numChildren]);

    const canSave = selectedTerritory &&
        dynastyName.length > 0 &&
        primaryCharacterName.length > 0 &&
        cultureGroup.length > 0 &&
        culture.length > 0 &&
        designerPoints <= 400;

    const triggerSave = () => {
        setErrorText('');
        setSaving(true);
        const payload = {
            territoryId: selectedTerritory,
            dynastyPrefix: dynastyNamePrefix,
            dynastyName,
            dynastyMotto,
            dynastyCoa,
            copyCoaToTile: copyCoa,
            primaryCharacterName,
            isFemale: primaryCharacterGender === 'female',
            sexualOrientation: sexualOrientation.toLowerCase(),
            cultureGroup,
            culture,
            primaryCharacterDna,
            primaryCharacterAge,
            traits: [educationTrait.internalName].concat(selectedTraits),
            baseDiplomacy: baseSkills.Diplomacy,
            baseIntrigue: baseSkills.Intrigue,
            baseMartial: baseSkills.Martial,
            baseLearning: baseSkills.Learning,
            baseStewardship: baseSkills.Stewardship,
            baseProwess: baseSkills.Prowess,
            hasSpouse: married,
            spouseName,
            childrenAge: childAges.toUpperCase(),
            children
        };

        axios.post("/api/characters", payload)
            .then(() => {
                history.push("/characters");
            })
            .catch(error => {
                if (error.response.status === 412) {
                    setErrorText('The selected territory has now been reserved by another player, please make another selection.');
                } else if (error.response.status === 409) {
                    setErrorText('There is another dynasty with the same name, please change the dynasty name.');
                } else if (error.response.status === 406) {
                    setErrorText('Too many customisation points spent.');
                } else {
                    setErrorText('An unknown error has occured');
                }
                setSaving(false);
            });

    }

    const addTo = (skill) => {
        const cloned = Object.assign({}, baseSkills);
        cloned[skill] = cloned[skill] + 1;
        setBaseSkills(cloned);
    }

    const removeFrom = (skill) => {
        const cloned = Object.assign({}, baseSkills);
        cloned[skill] = cloned[skill] - 1;
        setBaseSkills(cloned);
    }

    const buildSkillControl = (skill) => {
        return <SkillControl skill={skill} baseValue={baseSkills[skill]} value={skills[skill]}
                             onAdd={() => addTo(skill)} onRemove={() => removeFrom(skill)}
        />;
    };

    return (
        <React.Fragment>
            <Container>
                <Header as='h2'>Character Designer</Header>

                <p>If you're not familiar with the CK3 ruler designer, see <a
                    href="https://ck3.paradoxwikis.com/Ruler_Designer" target="_blank">here</a>.</p>

                {loading &&
                <Loader/>
                }

                {!loading &&
                <React.Fragment>
                    <Form>
                        <Header as='h3'>Territory</Header>
                        <p>For the next game of Crusader Kings, all players are starting as Norse chiefdoms of the Ásatrú faith in Scandinavia in 863.
                            Each starting territory is a parcel of 2 counties.</p>
                        <p>You can <a href={process.env.PUBLIC_URL + '/images/scandi_counties.png'} target='_blank'>
                            click here to see which counties are where
                        </a>.</p>

                        <Form.Select
                            label='Starting territory*'
                            value={selectedTerritory}
                            onChange={(event, data) => setSelectedTerritory(data.value)}
                            options={territoryOptionItems}
                        />


                        <Header as='h3'>Dynasty</Header>

                        <Input label='Dynasty name prefix' value={dynastyNamePrefix}
                               onChange={(event, data) => setDynastyNamePrefix(data.value)} fluid={true}
                               placeholder='(Optional) Use this to add a prefix to your dynasty name e.g. "von" or "de"'/>

                        <Input label='Dynasty name*' value={dynastyName}
                               onChange={(event, data) => setDynastyName(data.value)} fluid={true}
                               placeholder='The name of your dynasty (family name)'/>

                        <Input label='Dynasty motto' value={dynastyMotto}
                               onChange={(event, data) => setDynastyMotto(data.value)} fluid={true}
                               placeholder='(Optional) Your dynasty motto'/>

                        <Form.Field label='Dynasty Coat of Arms (use Copy to Clipboard in CK3 CoA designer)'
                                    value={dynastyCoa} onChange={(event) => setDynastyCoa(event.target.value)}
                                    control='textarea' rows='3'/>
                        <p>Need some inspiration for a Coat of Arms or just want to take someone's custom work? Try
                            visiting <a target='_blank' href='https://www.reddit.com/r/CKHeraldry/'>r/CKHeraldry</a>
                        </p>

                        <Form.Field
                            control={Checkbox}
                            checked={copyCoa}
                            onChange={(event, data) => setCopyCoa(data.checked)}
                            label={{children: 'Check this box to copy your dynasty coat of arms to your primary title i.e. your county or duchy coat of arms'}}
                        />


                        <Header as='h3'>Primary Character</Header>

                        <Input label='Character name*' value={primaryCharacterName}
                               onChange={(event, data) => setPrimaryCharacterName(data.value)} fluid={true}
                               placeholder="Primary character's given name (first name)"/>

                        <Form.Group inline>
                            <label>Gender</label>
                            <Form.Radio
                                label='Male'
                                value='male'
                                checked={primaryCharacterGender === 'male'}
                                onChange={() => setPrimaryCharacterGender('male')}
                            />
                            <Form.Radio
                                label='Female'
                                value='female'
                                checked={primaryCharacterGender === 'female'}
                                onChange={() => setPrimaryCharacterGender('female')}
                            />
                        </Form.Group>

                        <Form.Group inline>
                            <label>Sexual Orientation</label>
                            {['Random', 'Heterosexual', 'Homosexual', 'Bisexual', 'Asexual'].map(orientation =>
                                <Form.Radio
                                    key={orientation}
                                    label={orientation}
                                    value={orientation}
                                    checked={sexualOrientation === orientation}
                                    onChange={() => setSexualOrientation(orientation)}
                                />
                            )}
                        </Form.Group>

                        <Form.Select
                            label='Culture Group'
                            value={cultureGroup}
                            onChange={(event, data) => setCultureGroup(data.value)}
                            options={cultureGroupOptions}
                        />

                        <p>It is strongly recommended to keep this as North Germanic/Norse.</p>

                        <Form.Select
                            label='Culture'
                            value={culture}
                            onChange={(event, data) => setCulture(data.value)}
                            options={cultureOptions}
                        />

                        <Form.Field label='Character DNA (use Copy DNA in CK3 ruler designer)'
                                    value={primaryCharacterDna} onChange={(event) => setPrimaryCharacterDna(event.target.value)}
                                    control='textarea' rows='3'/>
                        <p>You can leave this blank for a random appearance based on your culture, or try visiting <a
                            target='_blank' href='https://www.reddit.com/r/CKTinder/'>r/CKTinder</a>
                        </p>

                        <Header as='h3'>Character Customisation</Header>

                        <p>You have up to 400 customisation points to spend on your primary character. Use the bar below
                            to check you are staying within the budget.</p>

                        <Progress progress='value' value={designerPoints} total={400} success={designerPoints <= 400}
                                  error={designerPoints > 400}/>

                        <Form.Field label='Age' control='input' type='number' min={16} max={100}
                                    onChange={(event, data) => setPrimaryCharacterAge(event.target.value)}
                                    value={primaryCharacterAge}/>
                        <Slider discrete value={primaryCharacterAge} settings={{
                            start: primaryCharacterAge,
                            min: 16,
                            max: 100,
                            step: 1,
                            onChange: setPrimaryCharacterAge
                        }}/>


                        <div className="field">
                            <label>Education</label>

                            <Form.Group inline>
                                <TraitButton internalName={educationTraitName}/>
                                <Button icon labelPosition='right' onClick={() => setShowEducations(!showEducations)}>
                                    {educationTrait.name}
                                    <Icon name='exchange'/>
                                </Button>
                            </Form.Group>

                        </div>

                        {showEducations &&
                        <Card.Group>
                            {educationTraits.map(trait =>
                                <Card
                                    className='education-card'
                                    link
                                    header={trait.name}
                                    meta={trait.skillModifiers[0].skill + ' ' + trait.type}
                                    description={<List>{buildTraitListItems(trait)}</List>}
                                    onClick={() => {
                                        setShowEducations(false);
                                        setEducationTraitName(trait.internalName)
                                    }}
                                />
                            )}
                        </Card.Group>
                        }


                        <div className="field">
                            <label>Traits</label>
                            <Form.Group inline>
                                {selectedTraits.length === 0 && 'None selected'}
                                {selectedTraits.map(selected => <TraitButton key={selected} internalName={selected}
                                                                             onClick={() => setSelectedTraits(selectedTraits.filter(t => t !== selected))}
                                />)}
                            </Form.Group>

                            <Button
                                onClick={() => setShowMoreTraits(!showMoreTraits)}>{showMoreTraits ? 'Hide' : 'Add more traits'}</Button>

                            {showMoreTraits && traitSections}
                        </div>


                        <h4>Skills</h4>

                        <Grid columns='equal'>
                            <Grid.Row>
                                <Grid.Column>
                                    {buildSkillControl('Diplomacy')}
                                </Grid.Column>
                                <Grid.Column>
                                    {buildSkillControl('Intrigue')}
                                </Grid.Column>
                            </Grid.Row>
                            <Grid.Row>
                                <Grid.Column>
                                    {buildSkillControl('Martial')}
                                </Grid.Column>
                                <Grid.Column>
                                    {buildSkillControl('Learning')}
                                </Grid.Column>
                            </Grid.Row>
                            <Grid.Row>
                                <Grid.Column>
                                    {buildSkillControl('Stewardship')}
                                </Grid.Column>
                                <Grid.Column>
                                    {buildSkillControl('Prowess')}
                                </Grid.Column>
                            </Grid.Row>
                        </Grid>


                        <Header as='h3'>Family</Header>

                        <Form.Field
                            control={Checkbox}
                            checked={married}
                            onChange={(event, data) => setMarried(data.checked)}
                            label={{children: 'Check this box if you want to start the game married with a randomly generated spouse'}}
                        />
                        {married &&
                        <Input label='Spouse name' value={spouseName}
                               onChange={(event, data) => setSpouseName(data.value)} fluid={true}
                               placeholder="Your spouse's given name (first name)"/>
                        }
                        <Form.Field label='Number of children' control='input' type='number' min={0} max={maxChildren}
                                    onChange={(event, data) => setNumChildren(event.target.value)}
                                    value={numChildren}/>
                        <p><i>Each child costs 10 customisation points</i></p>

                        {numChildren > 0 &&
                        <Form.Group inline>
                            <label>Age of children</label>
                            <Form.Radio
                                label='As young as possible'
                                value='young'
                                checked={childAges === 'young'}
                                onChange={() => setChildAges('young')}
                            />
                            <Form.Radio
                                label='Somewhere in the middle'
                                value='medium'
                                checked={childAges === 'medium'}
                                onChange={() => setChildAges('medium')}
                            />
                            <Form.Radio
                                label='As old as possible'
                                value='old'
                                checked={childAges === 'old'}
                                onChange={() => setChildAges('old')}
                            />
                        </Form.Group>
                        }

                        {childControls}

                    </Form>


                    {errorText &&
                    <Message negative>
                        <p>{errorText}</p>
                    </Message>
                    }

                    <Button primary onClick={triggerSave} disabled={saving || !canSave}>Save</Button>
                </React.Fragment>
                }
            </Container>
        </React.Fragment>
    );
};


export default CharacterDesignerPage;