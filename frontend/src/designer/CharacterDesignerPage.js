import {Slider} from "react-semantic-ui-range";
import React, {useEffect, useState} from "react";
import {
    Button, Card,
    Checkbox,
    Container,
    Form,
    Grid,
    Header,
    Icon, Image,
    Input, List,
    Loader,
    Message,
    Progress, Segment
} from "semantic-ui-react";
import cultureMapping from "./CultureMapping";
import ageCostCalculator from "./AgeCostCalculator";
import TraitButton, {buildTraitListItems} from "./TraitButton";
import TraitStore from "./TraitStore";
import skillCostCalculator from "./SkillCostCalculator";
import SkillControl from "./SkillControl";

const CharacterDesignerPage = ({loggedInPlayer}) => {

    const [loading, setLoading] = useState(false);
    const [errorText, setErrorText] = useState(false);

    const [dynastyNamePrefix, setDynastyNamePrefix] = useState('');
    const [dynastyName, setDynastyName] = useState('');
    const [dynastyMotto, setDynastyMotto] = useState('');
    const [dynastyCoa, setDynastyCoa] = useState('');
    const [copyCoa, setCopyCoa] = useState(false);

    const [primaryCharacterName, setPrimaryCharacterName] = useState('');
    const [primaryCharacterGender, setPrimaryCharacterGender] = useState('male');
    const [sexualOrientation, setSexualOrientation] = useState('Heterosexual');

    const [cultureGroup, setCultureGroup] = useState('');
    const [culture, setCulture] = useState('');
    const [primaryCharacterDNA, setPrimaryCharacterDNA] = useState('');

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
    console.log('excludedTraits', excludedTraits);
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

            {traits.map(t => <TraitButton key={t.internalName} internalName={t.internalName} onClick={() =>
                setSelectedTraits(selectedTraits.concat([t.internalName]))
            }/>)}
        </Segment>);
    }

    const [baseSkills, setBaseSkills] = useState({
        'Diplomacy': 0,
        'Intrigue': 0,
        'Martial': 0,
        'Learning': 0,
        'Stewardship': 0,
        'Prowess': 0,
    });

    const [skills, setSkills] = useState({
        'Diplomacy': 0,
        'Intrigue': 0,
        'Martial': 0,
        'Learning': 0,
        'Stewardship': 0,
        'Prowess': 0,
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


    const maxChildren = Math.max(0, primaryCharacterAge - 15);
    console.log('age, maxChildren', primaryCharacterAge, maxChildren);

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

    useEffect(() => {
        let pointsSpent = 0;
        pointsSpent += ageCostCalculator(primaryCharacterAge);
        pointsSpent += educationTrait.cost;
        selectedTraits.forEach(t => {
            pointsSpent += TraitStore.getByInternalName(t).cost;
        });
        setDesignerPoints(pointsSpent);
    }, [primaryCharacterAge, educationTraitName, selectedTraits]);

    const triggerSave = () => {
        // TODO check syntax of coat of arms
        // check dynasty name is specified

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

    console.log('Martial', baseSkills['Martial'], skills['Martial'])

    const buildSkillControl = (skill) => {
        return <SkillControl skill={skill} baseValue={baseSkills[skill]} value={skills[skill]}
            onAdd={() => addTo(skill)} onRemove={() => removeFrom(skill)}
        />;
    };

    return (
        <React.Fragment>
            <Container>
                <Header as='h2'>Character Designer</Header>

                <p>If you're not familiar with the CK3 ruler design, see <a
                    href="https://ck3.paradoxwikis.com/Ruler_Designer" target="_blank">here</a>.</p>

                {loading &&
                <Loader/>
                }

                {!loading &&
                <React.Fragment>
                    <Header as='h3'>Dynasty</Header>

                    <Form>
                        <Input label='Dynasty name prefix' value={dynastyNamePrefix}
                               onChange={(event, data) => setDynastyNamePrefix(data.value)} fluid
                               placeholder='(Optional) Use this to add a prefix to your dynasty name e.g. "von" or "de"'/>

                        <Input label='Dynasty name' value={dynastyName}
                               onChange={(event, data) => setDynastyName(data.value)} fluid
                               placeholder='The name of your dynasty (family name)'/>

                        <Input label='Dynasty motto' value={dynastyMotto}
                               onChange={(event, data) => setDynastyMotto(data.value)} fluid
                               placeholder='(Optional) Your dynasty motto'/>

                        <Form.Field label='Dynasty Coat of Arms (use Copy to Clipboard in CK3 CoA designer)'
                                    value={dynastyCoa} onChange={(data) => setDynastyCoa(data.value)}
                                    control='textarea' rows='3'/>
                        <p>Need some inspiration for a Coat of Arms or just want to take someone's custom work? Try
                            visiting <a target='_blank' href='https://www.reddit.com/r/CKHeraldry/'>r/CKHeraldry</a>
                        </p>

                        <Form.Field
                            control={Checkbox}
                            value={copyCoa}
                            onChange={(event, data) => setCopyCoa(data.value)}
                            label={{children: 'Check this box to copy your dynasty coat of arms to your primary title i.e. your county or duchy coat of arms'}}
                        />


                        <Header as='h3'>Primary Character</Header>

                        <Input label='Character name' value={primaryCharacterName}
                               onChange={(event, data) => setPrimaryCharacterName(data.value)} fluid
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

                        <p>If you're not sure which culture group/culture to pick, ask in Discord and we'll find the
                            culture of your initial holdings.</p>

                        <Form.Select
                            label='Culture'
                            value={culture}
                            onChange={(event, data) => setCulture(data.value)}
                            options={cultureOptions}
                        />

                        <Form.Field label='Character DNA (use Copy DNA in CK3 ruler designer)'
                                    value={primaryCharacterDNA} onChange={(data) => setPrimaryCharacterDNA(data.value)}
                                    control='textarea' rows='3'/>
                        <p>You can leave this blank for a random appearance based on your culture, or try visiting <a
                            target='_blank' href='https://www.reddit.com/r/CKTinder/'>r/CKTinder</a>
                        </p>

                        <Header as='h3'>Character Customisation</Header>

                        <p>You have up to 400 customisation points to spend on your primary character. Use the bar below
                            to check you are staying within the budget.</p>

                        <Progress progress='value' value={designerPoints} total={400} success={designerPoints <= 400}
                                  error={designerPoints > 400}/>

                        <Form.Field label='Age' control='input' type='number' min={16} max={100} fluid
                                    onChange={(event, data) => setPrimaryCharacterAge(event.target.value)}
                                    value={primaryCharacterAge}/>
                        <Slider discrete value={primaryCharacterAge} settings={{
                            start: primaryCharacterAge,
                            min: 16,
                            max: 100,
                            step: 1,
                            onChange: setPrimaryCharacterAge
                        }}/>


                        <div class="field" inline>
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


                        <div className="field" inline>
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

                    </Form>


                    {errorText &&
                    <Message negative>
                        <p>{errorText}</p>
                    </Message>
                    }

                    {/*<Button primary onClick={triggerSave}>Save</Button>*/}
                </React.Fragment>
                }
            </Container>
        </React.Fragment>
    );
};


export default CharacterDesignerPage;