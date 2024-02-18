package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Card;
import model.Player;
import utils.Color;
import utils.Number;


public class DaoImpl {

	private Connection conexion;

	public static final String SCHEMA_NAME = "practica_jdbc";
	public static final String CONNECTION = "jdbc:mysql://localhost:3307/" + SCHEMA_NAME
			+ "?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	public static final String USER_CONNECTION = "root";
	public static final String PASS_CONNECTION = null;

	public void connectar() throws SQLException {
		String url = CONNECTION;
		String user = USER_CONNECTION;
		String pass = PASS_CONNECTION;
		conexion = DriverManager.getConnection(url, user, pass);
	}

	public void desconectar() throws SQLException {
		if (conexion != null) {
			conexion.close();
		}
	}
	
	public Player getPlayer(String user, String pass) throws SQLException {
		Player player = null;
		String query = "SELECT * FROM Player WHERE user = ? AND password = ?";
		try (PreparedStatement statement = conexion.prepareStatement(query)) {
			statement.setString(1, user);
			statement.setString(2, pass);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					player = new Player(resultSet.getInt("id"), resultSet.getString("user"),
							resultSet.getString("password"), resultSet.getString("name"), resultSet.getInt("games"),
							resultSet.getInt("victories"));
				}
			}
		}
		return player;
	}
	
		public ArrayList<Card> getCards(int playerId) throws SQLException {
			ArrayList<Card> cards = new ArrayList<>();
			String query = "SELECT * FROM Card WHERE id_player = ?";
			try (PreparedStatement statement = conexion.prepareStatement(query)) {
				statement.setInt(1, playerId);
				try (ResultSet resultSet = statement.executeQuery()) {
					while (resultSet.next()) {
						Card card = new Card(resultSet.getInt("id"), resultSet.getString("color"),
								resultSet.getString("number"), resultSet.getInt("id_player"));
						cards.add(card);
					}
				}
			}
	
			return cards;
		}
	
		public int getLastIdCard(int playerId) throws SQLException {
			int lastId = 0;
			String query = "SELECT IFNULL(MAX(id), 0) + 1 FROM Card WHERE id_player = ?";
			try (PreparedStatement statement = conexion.prepareStatement(query)) {
				statement.setInt(1, playerId);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						lastId = resultSet.getInt(1);
					}
				}
			}  	 
			return lastId;
		}
	
		public Card getLastCard() throws SQLException {
			Card lastCard = null;
			String query = "SELECT c.* FROM Card c JOIN Game g ON c.id = g.id_card ORDER BY g.id DESC LIMIT 1";
			try (Statement statement = conexion.createStatement()) {
				try (ResultSet resultSet = statement.executeQuery(query)) {
					if (resultSet.next()) {
						lastCard = new Card(resultSet.getInt("id"), resultSet.getString("color"),
								resultSet.getString("number"), resultSet.getInt("id_player"));
					}
				}
			}
			return lastCard;
		}
	
		public void saveGame(Card card) throws SQLException {
			String query = "INSERT INTO Game (id_card) VALUES (?)";
			try (PreparedStatement statement = conexion.prepareStatement(query)) {
				statement.setInt(1, card.getId());
				statement.executeUpdate();
			}
		}
	
		public void saveCard(Card card) throws SQLException {
			String query = "INSERT INTO Card (id, id_player, number, color) VALUES (?, ?, ?, ?)";
			try (PreparedStatement statement = conexion.prepareStatement(query)) {
				statement.setInt(1, card.getId());
				statement.setInt(2, card.getPlayerId());
				statement.setString(3, card.getNumber());
				statement.setString(4, card.getColor());
				statement.executeUpdate();
			}
		}
	
		public void deleteCard(Card card) throws SQLException {
			String query = "DELETE FROM Game WHERE id_card = ?";
			try (PreparedStatement statement = conexion.prepareStatement(query)) {
				statement.setInt(1, card.getId());
				statement.executeUpdate();
			}
		}
	
		public void clearDeck(int playerId) throws SQLException {
			String queryGame = "DELETE FROM Game WHERE id_card IN (SELECT id FROM Card WHERE id_player = ?)";
			String queryCard = "DELETE FROM Card WHERE id_player = ?";
			try (PreparedStatement statementGame = conexion.prepareStatement(queryGame);
					PreparedStatement statementCard = conexion.prepareStatement(queryCard)) {
				statementGame.setInt(1, playerId);
				statementCard.setInt(1, playerId);
				statementGame.executeUpdate();
				statementCard.executeUpdate();
			}
		}
	
		public void addVictories(int playerId) throws SQLException {
			String query = "UPDATE Player SET victories = victories + 1 WHERE id = ?";
			try (PreparedStatement statement = conexion.prepareStatement(query)) {
				statement.setInt(1, playerId);
				statement.executeUpdate();
			}
		}
	
		public void addGames(int playerId) throws SQLException {
			String query = "UPDATE Player SET games = games + 1 WHERE id = ?";
			try (PreparedStatement statement = conexion.prepareStatement(query)) {
				statement.setInt(1, playerId);
				statement.executeUpdate();
			}
		}
	
		public Card getCard(int cardId) throws SQLException {
			Card card = null;
			String query = "SELECT * FROM Card WHERE id = ?";
			try (PreparedStatement statement = conexion.prepareStatement(query)) {
				statement.setInt(1, cardId);
				try (ResultSet resultSet = statement.executeQuery()) {
					if (resultSet.next()) {
						card = new Card(resultSet.getInt("id"), resultSet.getString("color"), resultSet.getString("number"), resultSet.getInt("id_player"));
					}
				}
			}
			return card;
		}
	
	}