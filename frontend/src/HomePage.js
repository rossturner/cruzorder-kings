import {Container, Header} from "semantic-ui-react";

const HomePage = () => {

    return (
        <Container>
            <Container textAlign='center' style={{marginBottom: '1em'}}>
                <Header as='h2'>Welcome to Cruzorder Kings!</Header>
            </Container>
            <Container text>
                <p>
                    This site is used to create your CK3 characters for use in Harringzord's CK3 games.
                </p>
                <p>
                    Cruzorder Kings is a creation of <a href='https://discord.gg/Bq6p7yJHKd' target='_blank' rel="noreferrer">the Disczord community</a>,
                    home of Twitch streamer <a href='https://www.twitch.tv/harringzord' target='_blank' rel="noreferrer">Harringzord</a>.
                </p>
                <p>
                    If that sounds like something you'd like to take part in, join <a href='https://discord.gg/Bq6p7yJHKd' target='_blank' rel="noreferrer">the Discord server</a>,
                    read the welcome page and get involved!
                </p>
            </Container>
        </Container>
    );
};

export default HomePage;