% DAYS:     tuesday,    wednesday,      thursday,       friday
% NAMES:    arend,      marieke,        marco,          jaco
% CASTLES:  twickel,    westerflier,    weldam,         espelo
% ICE:      peppermint, coffee_bean,    peanut_butter,  choc_chip

% DAY, NAME, CASTLE, ICE

go(X) :- X =    [   stand(tuesday   ,Name_tue,Castle_tue,Ice_tue),
                    stand(wednesday ,Name_wed,Castle_wed,Ice_wed),
                    stand(thursday  ,Name_thu,Castle_thu,Ice_thu),
                    stand(friday    ,Name_fri,Castle_fri,Ice_fri)   ],

                    member(jaco,[Name_tue,Name_wed,Name_thu,Name_fri]),
                    member(arend,[Name_tue,Name_wed,Name_thu,Name_fri]),
                    member(marco,[Name_tue,Name_wed,Name_thu,Name_fri]),
                    member(marieke,[Name_tue,Name_wed,Name_thu,Name_fri]),

                    member(twickel,[Castle_tue,Castle_wed,Castle_thu,Castle_fri]),
                    member(westerflier,[Castle_tue,Castle_wed,Castle_thu,Castle_fri]),
                    member(weldam,[Castle_tue,Castle_wed,Castle_thu,Castle_fri]),
                    member(espelo,[Castle_tue,Castle_wed,Castle_thu,Castle_fri]),

                    member(peppermint,[Ice_tue,Ice_wed,Ice_thu,Ice_fri]),
                    member(coffee_bean,[Ice_tue,Ice_wed,Ice_thu,Ice_fri]),
                    member(peanut_butter,[Ice_tue,Ice_wed,Ice_thu,Ice_fri]),
                    member(choc_chip,[Ice_tue,Ice_wed,Ice_thu,Ice_fri]),

                    % 1
                    not(member(stand(_,arend,twickel,_),X)),
                    not(member(stand(thursday,_,_,peppermint),X)),
                    % 2
                    member(stand(wednesday,_,_,coffee_bean),X),
                    not(member(stand(wednesday,marieke,_,coffee_bean),X)),
                    % 3
                    member(stand(_,jaco,_,peanut_butter),X),
                    not(member(stand(tuesday,jaco,_,peanut_butter),X)),

                    % 4
                    member(stand(DayWest,_,westerflier,_),X),
                    before(DayWest, BeforeWest),
                    before(AfterWest, DayWest),
                    member(stand(BeforeWest,marco,_,_),X),
                    member(stand(AfterWest,_,_,choc_chip),X),

                    % 5
                    member(stand(Day3,arend,_,_),X),
                    before(Day3, Yesterday3),
                    member(stand(Yesterday3,_,weldam,_),X).

                    % 6
                    % member(stand(_,_,espelo,_),X).

before(wednesday, tuesday).
before(thursday, wednesday).
before(friday,thursday).
