import {Button, Grid, Icon, Image} from "semantic-ui-react";

const SkillControl = ({skill, baseValue, value, onAdd, onRemove}) => {

    const getRating = (value) => {
        if (value === 69) {
            return 'Nice';
        } else if (value <= 4) {
            return 'Terrible';
        } else if (value <= 8) {
            return 'Poor';
        } else if (value <= 12) {
            return 'Average';
        } else if (value <= 16) {
            return 'Good';
        } else {
            return 'Excellent';
        }
    }

    return <Grid>
        <Grid.Column width={1}>
            <Image src={process.env.PUBLIC_URL + '/media/' + skill + '.png'} />
        </Grid.Column>
        <Grid.Column width={3}>
            <h5>{skill}</h5>
            <p><i>{getRating(value)}</i></p>
        </Grid.Column>
        <Grid.Column width={8}>
            <Button icon disabled={baseValue <= 0} onClick={onRemove}>
                <Icon name='minus circle' />
            </Button>
            <b>{Math.max(value, 0)}</b>&nbsp;
            <Button icon disabled={baseValue >= 99 || value >= 99} onClick={onAdd}>
                <Icon name='plus circle' />
            </Button>
            (Base value {baseValue})
        </Grid.Column>
    </Grid>;
}

export default SkillControl;