
public class Blackjack	
{
	public static void main(String [] args)	
	{
		/*INITIALISATION*/ // yes i spelled w an s bc i m british, v brit, 10/10 London flowing thru my blood
		
		//put dem command line arguments to WORK.! work (angelicaaaa) #hamilton
		int numRounds = Integer.parseInt(args[0]);
		int numDecks = Integer.parseInt(args[1]);
		
		//set up and populate shoe based on command line args, shuffle shoe
		RandIndexQueue<Card> shoe = new RandIndexQueue<Card>(52);
		for(int j = 0; j < numDecks ; j++)
		{
			for(Card.Suits s : Card.Suits.values())
			{
				for(Card.Ranks r: Card.Ranks.values())
				{
					shoe.offer(new Card(s,r));
				}
			}
		}
		shoe.shuffle();
		
		//set up queue for player and dealer and even maybe a discard pile (if you couldn't already tell idk)
		RandIndexQueue<Card> player = new RandIndexQueue<Card>(4); //don't be messing with my *hearts* tho
		RandIndexQueue<Card> dealer = new RandIndexQueue<Card>(4); //the only crack involved here is the joke i'm trying to make out of this #420blazeit
		RandIndexQueue<Card> discard = new RandIndexQueue<Card>(4); //it's one letter away from discord!
		
		//win/loss stats tracking for player and dealer
		int playerValue=0;
		int dealerValue=0;
		
		int dealerWins=0;
		int playerWins=0;
		int ties=0;
		
		//make the user feel @ home :'))
		System.out.println("Starting Blackjack with "+numRounds+" rounds and "+numDecks+" decks in the shoe");
		
		/*PROGRAM LOGIC BEGINS HERE*/
		
		//full version for 10 rounds or less
		if(numRounds<=10)
		{
			//repeats depending on how many rounds are specified in the initial arguments
			for(int i = 0; i < numRounds ; i++)
			{	
				/*ACTUAL GAME*/
				System.out.println("\nRound "+i+" beginning");
				
				//deal two cards to both player and dealer
				//YES loop unrolling is more efficient don't @ me
				player.offer(shoe.poll());
				dealer.offer(shoe.poll());
				player.offer(shoe.poll());
				dealer.offer(shoe.poll());
					
				//determine player and dealer hand value after initial dealing. operating under the assumption that order matters, and thus if two aces are dealt, the first is 11 and the second is 1.
				playerValue=player.get(0).value();
				if(player.get(0).value()==11)
				{
					playerValue=playerValue+player.get(1).value2();
				}
				else
				{
					playerValue=playerValue+player.get(1).value();
				}
				
				dealerValue=dealer.get(0).value();
				if(dealer.get(0).value()==11)
				{
					dealerValue=dealerValue+dealer.get(1).value2();
				}
				else
				{
					dealerValue=dealerValue+dealer.get(1).value();
				}
				
				System.out.println("Player: "+player+" : "+playerValue);
				System.out.println("Dealer: "+dealer+" : "+dealerValue);
				
				//test for blackjack after the initial dealing of cards
				if(playerValue==21&&dealerValue!=21)
				{
					playerWins++;
					System.out.println("Result: Player Wins!\n");
				}
				else if(dealerValue==21&&playerValue!=21)
				{
					dealerWins++;
					System.out.println("Result: Dealer Wins!\n");
				}
				else if(playerValue==21&&dealerValue==21)
				{
					ties++;
					System.out.println("Result: Push!\n");
				}
				else
				{
					//player continues to hit until hand value exceeds 17
					while(playerValue<17)
					{
						//hit
						
						player.offer(shoe.poll());
						System.out.println("Player hits: "+player.get(player.size()-1));
						
						//re-evaluate player value
						
						if((playerValue+player.get(player.size()-1).value())>21)
						{
							playerValue = playerValue + player.get(player.size()-1).value2();
						}
						else
						{
							playerValue = playerValue + player.get(player.size()-1).value();
						}
						
					}
					
					//player busts
					if(playerValue>21)
					{
						//shows user what made them bust (dey nuts)
						System.out.println("Player Busts! "+player+" : "+playerValue);
						
						//records dealer as having won
						dealerWins++;
						System.out.println("Result: Dealer Wins!\n");
					}
					
					//player stands
					else
					{
						//shows user what the *deal* is with the player deck
						System.out.println("Player Stands! "+player+" : "+playerValue);							
						
						while(dealerValue<17)
						{
							//hit
							
							dealer.offer(shoe.poll());
							System.out.println("Dealer hits: "+dealer.get(dealer.size()-1));
							
							//re-evaluate dealer value
						
							if((dealerValue+dealer.get(dealer.size()-1).value())>21)
							{
								dealerValue = dealerValue + dealer.get(dealer.size()-1).value2();
							}
							else
							{
								dealerValue = dealerValue + dealer.get(dealer.size()-1).value();
							}
								
						}
						
						//dealer busts
						if(dealerValue>21)
						{
							//shows user what made them bust (dey nutsssss)
							System.out.println("Dealer Busts! "+dealer+" : "+dealerValue);
							
							//records player as having won
							playerWins++;
							System.out.println("Result: Player Wins!\n");
						}
						
						//dealer stands
						else
						{
							//shows user where the dealer at
							System.out.println("Dealer Stands! "+dealer+" : "+dealerValue);
							
							//final results
							if(playerValue>dealerValue)
							{
								playerWins++;
								System.out.println("Result: Player Wins!\n");
							}
							else if(dealerValue>playerValue)
							{
								dealerWins++;
								System.out.println("Result: Dealer Wins!\n");
							}
							else
							{
								ties++;
								System.out.println("Result: Push!\n");
							}
						}
						
					}
				}
				
				
				
				/*END OF GAME*/
				
				//reset hand values
				playerValue=0;
				dealerValue=0;
				
				//take all cards that were played and put them in the discard pile
				
				int numItemsToRemovePlayer = player.size();
				for(int j = 0; j < numItemsToRemovePlayer; j++)
				{
					//System.out.println("curr player array "+player);
					
					discard.offer(player.poll());
					
				}
				
				int numItemsToRemoveDealer = dealer.size();
				for(int j = 0; j < numItemsToRemoveDealer; j++)
				{
					//System.out.println("curr dealer array "+dealer);
					discard.offer(dealer.poll());
					
				}
				
				//check the size of the shoe and re-add discard pile and shuffle if the shoe is at 0.25 of its og size or less
				if(shoe.size()<=0.25*52*numDecks)
				{
					for(int k = 0; k <= discard.size() ; k++)
					{
						shoe.offer(discard.poll());
					}
					shoe.shuffle();
					System.out.println("Reshuffling the shoe in round "+i);
				}
			}
			
		}
		
		//abbreviated version for more than 10 players
		else
		{
			//do the other thing	
		}
		
		/*ALL ROADS LEAD BACK TO ROME (OR ALL BRANCHING AT LEAST LEADS BACK TO THE END OF MY PROGRAM)*/
		System.out.println("After "+numRounds+" rounds, here are the results: ");
		System.out.println("\tDealer Wins: "+dealerWins);
		System.out.println("\tPlayer Wins: "+playerWins);
		System.out.println("\tPushes: "+ties);
	}

}