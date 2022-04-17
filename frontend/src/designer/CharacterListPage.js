import {Button, Container, Header, Placeholder, Table} from "semantic-ui-react";
import React, {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";
import PlayerAvatar from "../player/PlayerAvatar";


const CharacterListPage = ({loggedInPlayer}) => {

    const [loading, setLoading] = useState(true);
    const [characters, setCharacters] = useState([]);
    // let history = useHistory();

    const canCreateCharacters = characters.length <= 0 || loggedInPlayer.isAdmin;

    useEffect(() => {
        axios.get('/api/characters')
            .then((response) => {
                setCharacters(response.data);
                setLoading(false);
            })
            .catch((error) => {
                console.error('Error retrieving characters', error);
            })
    }, []);

    let bodyRows = [];

    characters.forEach(data => {
        bodyRows.push(<Table.Row key={data.character.baseId}>
            <Table.Cell>
                <PlayerAvatar player={data.player} size='mini'/>
                {data.player.discordUsername}
            </Table.Cell>
            <Table.Cell>{data.character.primaryCharacterName}</Table.Cell>
            <Table.Cell>{data.character.dynastyName}</Table.Cell>
            <Table.Cell>{data.territory.displayName}</Table.Cell>
            <Table.Cell><Button as={Link} to={'/designer/'+data.character.baseId}>Edit</Button></Table.Cell>
        </Table.Row>);
    });

    return (
        <React.Fragment>
            <Container>
                <Header as='h2'>
                    {loggedInPlayer.isAdmin && 'All characters'}
                    {!loggedInPlayer.isAdmin && loggedInPlayer.discordUsername+"'s Characters"}
                </Header>
                {loading &&
                    <Placeholder>
                        <Placeholder.Line />
                        <Placeholder.Line />
                        <Placeholder.Line />
                        <Placeholder.Line />
                        <Placeholder.Line />
                    </Placeholder>
                }

                {!loading &&
                <React.Fragment>

                    {characters.length > 0 &&
                        <Table celled>
                            <Table.Header>
                                <Table.Row>
                                    <Table.HeaderCell>Player</Table.HeaderCell>
                                    <Table.HeaderCell>Character Name</Table.HeaderCell>
                                    <Table.HeaderCell>Dynasty Name</Table.HeaderCell>
                                    <Table.HeaderCell>Territory</Table.HeaderCell>
                                    <Table.HeaderCell></Table.HeaderCell>
                                </Table.Row>
                            </Table.Header>

                            <Table.Body>
                                {bodyRows}
                            </Table.Body>
                        </Table>
                    }

                    {characters.length === 0 && <p>You have not yet created any characters.</p>}

                    {canCreateCharacters && <Button as={Link} to={'/designer/new'} primary>Create new</Button>}
                </React.Fragment>
                }
            </Container>
        </React.Fragment>
    );
};


export default CharacterListPage;